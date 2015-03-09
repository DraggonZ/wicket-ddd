package promolo.wicket.core.ui.notification;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author lexx
 */
public interface DomainEventNotificationListener {

    public void notify(@Nonnull DomainEvent domainEvent);

}
