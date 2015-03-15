package promolo.wicket.core.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import promolo.wicket.core.ui.notification.DomainEventNotificationManager;

/**
 * TODO javadoc
 *
 * @author lexx
 */
@ApplicationScoped
public class CdiDomainEventNotificationManager {

    @Produces
    @RequestScoped
    public DomainEventNotificationManager createDomainEventNotificationManager() {
        return new DomainEventNotificationManager();
    }

    public void destroyDomainEventNotificationManager(@Disposes DomainEventNotificationManager domainEventNotificationManager) {
        domainEventNotificationManager.clear();
    }

}
