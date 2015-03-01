package promolo.wicket.core.ui.component;

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
 */
public class ViewEventForwardingBehavior extends Behavior {

    private final Serializable eventSink;

    public ViewEventForwardingBehavior(@Nonnull Serializable eventSink) {
        super();
        this.eventSink = eventSink;
    }

    @Override
    public final void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        if (canBeForwarded(event)) {
            forwardTo(this.eventSink, event.getPayload());
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    protected boolean canBeForwarded(@Nonnull IEvent<?> event) {
        if (event.getSource() instanceof Component) {
            return (event.getPayload() != null && !(event.getPayload() instanceof AjaxRequestHandler));
        } else {
            return false;
        }
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
            throw new IllegalStateException("ошибка вызова метода " + methodName + " в классе " + presenterClass.getName(), ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("метод " + methodName + " в классе " + presenterClass.getName() + " должен быть public", ex);
        }
    }

}
