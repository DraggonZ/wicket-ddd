package promolo.wicket.core.domain;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class EventCatcher<T> implements DomainEventSubscriber<T> {

    private final Class<T> domainEventClass;

    private T event;

    private boolean cached;

    @Nonnull
    public static <T> EventCatcher<T> of(@Nonnull Class<T> domainEventClass) {
        return new EventCatcher<>(domainEventClass);
    }

    @Override
    public void handleEvent(@Nonnull T domainEvent) {
        this.event = domainEvent;
        this.cached = true;
    }

    @Nonnull
    @Override
    public Class<T> subscribedToEventType() {
        return this.domainEventClass;
    }

    @CheckForNull
    public T event() {
        return this.event;
    }

    public boolean cached() {
        return this.cached;
    }

    public void reset() {
        this.event = null;
        this.cached = false;
    }

    private EventCatcher(@Nonnull Class<T> domainEventClass) {
        this.domainEventClass = domainEventClass;
    }

}
