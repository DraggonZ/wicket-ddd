package promolo.wicket.core.domain;

import javax.annotation.Nonnull;

public interface DomainEventSubscriber<T> {

    public void handleEvent(@Nonnull T domainEvent);

    @Nonnull
    public Class<T> subscribedToEventType();

}
