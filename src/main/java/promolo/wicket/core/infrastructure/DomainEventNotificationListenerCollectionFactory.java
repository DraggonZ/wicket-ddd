package promolo.wicket.core.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import promolo.wicket.core.ui.notification.DomainEventNotificationListenerCollection;

/**
 * @author lexx
 */
@ApplicationScoped
public class DomainEventNotificationListenerCollectionFactory {

    @Produces
    @RequestScoped
    public DomainEventNotificationListenerCollection create() {
        return new DomainEventNotificationListenerCollection();
    }

    public void destroy(@Disposes DomainEventNotificationListenerCollection collection) {
        collection.clear();
    }

}
