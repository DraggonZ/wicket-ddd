package promolo.wicket.core.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import promolo.wicket.core.domain.DomainEventPublisher;

/**
 * @author lexx
 */
@ApplicationScoped
public class CdiDomainEventServiceEnvironment {

    public void cleanupDomainEventService(@Observes @Destroyed(RequestScoped.class) Object event) {
        DomainEventPublisher.clear();
    }

}
