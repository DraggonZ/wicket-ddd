package promolo.wicket.core.ui.notification;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 * @see promolo.wicket.core.stereotype.PresenterInstance
 */
public class ViewUserEventListener extends Behavior {

    private final Serializable presenter;

    public ViewUserEventListener(@Nonnull Serializable presenter) {
        super();
        this.presenter = presenter;
    }

    @Override
    public final void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        if (canBeForwarded(event)) {
            forwardTo(this.presenter, event.getPayload());
        }
    }

    protected boolean canBeForwarded(@Nonnull IEvent<?> event) {
        return (event.getSource() instanceof Component && (event.getPayload() != null && !(event
                .getPayload() instanceof AjaxRequestHandler)));
    }

    // TODO реализация - не оптимальная, нужно использовать кэш методов-обработчиков
    private static void forwardTo(@Nonnull Object eventSink, @Nonnull Object payload) {
        Class<?> presenterClass = eventSink.getClass();
        Class<?> eventClass = payload.getClass();
        String methodName = "on" + eventClass.getSimpleName();
        try {
            Method eventHandlerMethod = presenterClass.getMethod(methodName, eventClass);
            eventHandlerMethod.invoke(eventSink, payload);
        } catch (NoSuchMethodException ex) {
            // nop
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof RuntimeException) {
                throw (RuntimeException) ex.getCause();
            }
            throw new IllegalStateException("ошибка вызова метода " + methodName + " в классе " + presenterClass.getName(), ex.getCause());
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("метод " + methodName + " в классе " + presenterClass.getName() + " должен быть public", ex);
        }
    }

}
