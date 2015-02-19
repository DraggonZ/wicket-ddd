package promolo.wicket.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class DomainEventPublisher {

    private static final ThreadLocal<DomainEventPublisher> THREAD_LOCAL = new ThreadLocal<DomainEventPublisher>() {

        protected DomainEventPublisher initialValue() {
            return new DomainEventPublisher();
        }

    };

    private boolean publishing;

    private List<DomainEventSubscriber<?>> subscribers;

    @Nonnull
    public static DomainEventPublisher instance() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        instance().reset();
        THREAD_LOCAL.remove();
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(@Nonnull T domainEvent) {
        if (!isPublishing() && hasSubscribers()) {
            try {
                setPublishing(true);
                Class<?> eventType = domainEvent.getClass();
                for (DomainEventSubscriber<?> subscriber : subscribers()) {
                    Class<?> subscribedToType = subscriber.subscribedToEventType();
                    if (eventType == subscribedToType || subscribedToType == DomainEvent.class) {
                        ((DomainEventSubscriber<T>) subscriber).handleEvent(domainEvent);
                    }
                }
            } finally {
                setPublishing(false);
            }
        }
    }

    public void reset() {
        if (!isPublishing()) {
            setSubscribers(null);
        }
    }

    public <T> void subscribe(@Nonnull DomainEventSubscriber<T> aSubscriber) {
        if (!isPublishing()) {
            ensureSubscribersList();
            subscribers().add(aSubscriber);
        }
    }

    private DomainEventPublisher() {
        super();
        setPublishing(false);
        ensureSubscribersList();
    }

    @SuppressWarnings("rawtypes")
    private void ensureSubscribersList() {
        if (!hasSubscribers()) {
            setSubscribers(new ArrayList<DomainEventSubscriber<?>>());
        }
    }

    private boolean isPublishing() {
        return this.publishing;
    }

    private void setPublishing(boolean aFlag) {
        this.publishing = aFlag;
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    private List<DomainEventSubscriber<?>> subscribers() {
        return this.subscribers;
    }

    private void setSubscribers(List<DomainEventSubscriber<?>> subscriberList) {
        this.subscribers = subscriberList;
    }
}
