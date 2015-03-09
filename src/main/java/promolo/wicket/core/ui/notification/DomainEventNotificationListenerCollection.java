package promolo.wicket.core.ui.notification;

import java.util.List;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

import com.google.common.collect.Lists;

/**
 * TODO javadoc
 *
 * @author lexx
 */
public class DomainEventNotificationListenerCollection implements DomainEventNotificationListener {

    private List<DomainEventNotificationListener> domainEventNotificationListeners;

    public void addListener(@Nonnull DomainEventNotificationListener listener) {
        if (this.domainEventNotificationListeners == null) {
            this.domainEventNotificationListeners = Lists.newArrayList();
        }
        this.domainEventNotificationListeners.add(listener);
    }

    public void removeListener(@Nonnull DomainEventNotificationListener listener) {
        if (this.domainEventNotificationListeners != null) {
            this.domainEventNotificationListeners.remove(listener);
        }
    }

    @Override
    public void notify(@Nonnull DomainEvent domainEvent) {
        if (this.domainEventNotificationListeners != null) {
            for (DomainEventNotificationListener domainEventNotificationListener : this.domainEventNotificationListeners) {
                domainEventNotificationListener.notify(domainEvent);
            }
        }
    }

    public void clear() {
        if (this.domainEventNotificationListeners != null) {
            this.domainEventNotificationListeners.clear();
        }
    }

}
