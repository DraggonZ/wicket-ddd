package promolo.wicket.core.infrastructure;

import javax.enterprise.context.ApplicationScoped;

import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.DomainEventPublisherCleaner;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
public class CdiDomainEventPublisherCleaner implements DomainEventPublisherCleaner {

    @Override
    public void cleanup() {
        DomainEventPublisher.clear();
    }

}
