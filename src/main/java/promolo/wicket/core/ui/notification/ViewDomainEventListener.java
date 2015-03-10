package promolo.wicket.core.ui.notification;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author lexx
 * @see promolo.wicket.core.ui.notification.DomainEventNotificationListenerCollection
 */
public class ViewDomainEventListener extends Behavior implements DomainEventNotificationListener {

    private final DomainEventNotificationListenerCollection domainEventNotificationListenerCollection;

    private transient Page page; // behavior - временный (RequestScoped)

    public ViewDomainEventListener(@Nonnull DomainEventNotificationListenerCollection domainEventNotificationListenerCollection) {
        super();
        this.domainEventNotificationListenerCollection = domainEventNotificationListenerCollection;
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        if (component instanceof Page) {
            setPage((Page) component);
        } else {
            throw new IllegalStateException("компонента может быть добавлена только на страницу (Page)");
        }
        domainEventNotificationListenerCollection().addListener(this);
    }

    @Override
    public void unbind(Component component) {
        super.unbind(component);
        setPage(null);
        domainEventNotificationListenerCollection().removeListener(this);
    }

    @Override
    public boolean isTemporary(Component component) {
        return true;
    }

    @Override
    public void notify(@Nonnull DomainEvent domainEvent) {
        if (page() != null) {
            page().send(page(), Broadcast.BREADTH, domainEvent);
        }
    }

    @Nonnull
    private DomainEventNotificationListenerCollection domainEventNotificationListenerCollection() {
        return this.domainEventNotificationListenerCollection;
    }

    private void setPage(Page page) {
        this.page = page;
    }

    private Page page() {
        return this.page;
    }

}
