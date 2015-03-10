package promolo.wicket.core.ui.notification;

import javax.annotation.Nonnull;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author lexx
 */
public class AjaxRefreshOnDomainEvent extends Behavior {

    private final String domainEventClassName;

    private transient Class<? extends DomainEvent> domainEventClass;

    @Nonnull
    public static Behavior of(@Nonnull Class<? extends DomainEvent> domainEventClass) {
        return new AjaxRefreshOnDomainEvent(domainEventClass);
    }

    public AjaxRefreshOnDomainEvent(@Nonnull Class<? extends DomainEvent> domainEventClass) {
        super();
        this.domainEventClass = domainEventClass;
        this.domainEventClassName = domainEventClass.getName();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        component.setOutputMarkupId(true);
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        if (getDomainEventClass().isInstance(event.getPayload())) {
            AjaxRequestHandler ajaxRequestHandler = component.getRequestCycle().find(AjaxRequestHandler.class);
            if (ajaxRequestHandler != null) {
                ajaxRequestHandler.add(component);
                if (event.getType() == Broadcast.BREADTH) {
                    event.dontBroadcastDeeper();
                }
            }
        }
    }

    @Nonnull
    private Class<? extends DomainEvent> getDomainEventClass() {
        if (this.domainEventClass == null) {
            try {
                IClassResolver classResolver = Application.get().getApplicationSettings().getClassResolver();
                this.domainEventClass = (Class<? extends DomainEvent>) classResolver.resolveClass(this.domainEventClassName);
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("ошибка при загрузке класса " + this.domainEventClassName);
            }
        }
        return this.domainEventClass;
    }

}
