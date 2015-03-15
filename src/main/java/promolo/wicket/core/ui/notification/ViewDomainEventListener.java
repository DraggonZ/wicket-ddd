package promolo.wicket.core.ui.notification;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author lexx
 * @see DomainEventNotificationManager
 */
public class ViewDomainEventListener extends Behavior implements DomainEventNotificationListener {

    @Inject
    private DomainEventNotificationManager domainEventNotificationManager;

    private transient Page page; // behavior - временный (существует в пределах одного запроса)

    public ViewDomainEventListener() {
        super();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        if (component instanceof Page) {
            setPage((Page) component);
        } else {
            throw new IllegalStateException("компонента может быть добавлена только на страницу (Page)");
        }
        domainEventNotificationManager().addListener(this);
    }

    @Override
    public void unbind(Component component) {
        super.unbind(component);
        setPage(null);
        domainEventNotificationManager().removeListener(this);
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

    private void setPage(Page page) {
        this.page = page;
    }

    private Page page() {
        return this.page;
    }

    @Nonnull
    private DomainEventNotificationManager domainEventNotificationManager() {
        return this.domainEventNotificationManager;
    }

}
