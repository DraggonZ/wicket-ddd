package promolo.wicket.core.domain;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class DomainObject {

    protected DomainObject() {
        // nop
    }

    protected final void publish(@Nonnull DomainEvent domainEvent) {
        DomainEventPublisher.instance().publish(domainEvent);
    }

}
