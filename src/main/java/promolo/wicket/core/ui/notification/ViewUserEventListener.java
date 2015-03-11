package promolo.wicket.core.ui.notification;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.cdi.NonContextual;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.util.collections.ClassMetaCache;

/**
 * TODO javadoc
 *
 * @author Александр
 * @see promolo.wicket.core.stereotype.PresenterInstance
 * @see org.apache.wicket.cdi.NonContextual#inject(Object)
 */
public class ViewUserEventListener extends Behavior {

    private static final ClassMetaCache<Map<String, Method>> EVENT_HANDLER_MAP_CACHE = new ClassMetaCache<>();

    private static final String EVENT_HANDLER_PREFIX = "on";

    private final Serializable presenter;

    public ViewUserEventListener(@Nonnull Serializable presenter) {
        super();
        this.presenter = presenter;
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        NonContextual.of(presenter().getClass()).inject(presenter());
    }

    @Override
    public final void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        if (canBeForwarded(event)) {
            forwardTo(presenter(), event.getPayload());
        }
    }

    protected boolean canBeForwarded(@Nonnull IEvent<?> event) {
        return (event.getSource() instanceof Component && (event.getPayload() != null && !(event
                .getPayload() instanceof AjaxRequestHandler)));
    }

    @Nonnull
    private Object presenter() {
        return this.presenter;
    }

    private static void forwardTo(@Nonnull Object eventSink, @Nonnull Object event) {
        Class<?> eventSinkType = eventSink.getClass();
        Map<String, Method> eventHandlerMap = EVENT_HANDLER_MAP_CACHE.get(eventSinkType);
        if (eventHandlerMap == null) {
            eventHandlerMap = mapEventHandlers(eventSinkType);
            storeEventHandlers(eventSinkType, eventHandlerMap);
        }
        String methodName = eventHandlerNameByEvent(event);
        Method eventHandler = eventHandlerMap.get(methodName);
        if (eventHandler != null) {
            invokeEventHandler(eventHandler, eventSink, event);
        }
    }

    private static void storeEventHandlers(@Nonnull Class<?> eventSinkType, @Nonnull Map<String, Method> eventHandlerMap) {
        if (eventHandlerMap.isEmpty()) {
            EVENT_HANDLER_MAP_CACHE.put(eventSinkType, Collections.<String, Method>emptyMap());
        } else {
            EVENT_HANDLER_MAP_CACHE.put(eventSinkType, eventHandlerMap);
        }
    }

    @Nonnull
    private static Map<String, Method> mapEventHandlers(@Nonnull Class<?> eventSinkType) {
        Map<String, Method> eventHandlerMap = new HashMap<>();
        for (Method method : eventSinkType.getMethods()) {
            if (method.getName().startsWith(EVENT_HANDLER_PREFIX) && method.getReturnType().equals(Void.TYPE)
                    && method.getParameterCount() == 1) {
                eventHandlerMap.put(method.getName(), method);
            }
        }
        return eventHandlerMap;
    }

    @Nonnull
    private static String eventHandlerNameByEvent(@Nonnull Object event) {
        return EVENT_HANDLER_PREFIX + event.getClass().getSimpleName();
    }

    private static void invokeEventHandler(@Nonnull Method eventHandler, @Nonnull Object sink, @Nonnull Object event) {
        try {
            eventHandler.invoke(sink, event);
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof RuntimeException) {
                throw (RuntimeException) ex.getCause();
            }
            throw new IllegalStateException("ошибка вызова метода " + eventHandler.getName() + " в классе " + sink.getClass().getName(),
                    ex.getCause());
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                    "метод " + eventHandler.getName() + " в классе " + sink.getClass().getName() + " должен быть public", ex);
        }
    }

}
