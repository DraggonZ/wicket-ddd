package promolo.wicket.core.ui.component;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;

import promolo.wicket.core.domain.DomainEvent;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.DomainEventSubscriber;

/**
 * @author lexx
 */
public class ViewDomainEventListener extends Behavior implements DomainEventSubscriber<DomainEvent> {

    private Page page;

    public ViewDomainEventListener() {
        super();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        if (component instanceof Page) {
            this.page = (Page) component;
        } else {
            throw new IllegalStateException("компонента может быть добавлена только на страницу (Page)");
        }
        DomainEventPublisher.instance().subscribe(this);
    }

    @Override
    public void unbind(Component component) {
        super.unbind(component);
        this.page = null;
    }

    @Override
    public boolean isTemporary(Component component) {
        return true;
    }

    @Override
    public void handleEvent(@Nonnull DomainEvent domainEvent) {
        if (this.page != null) {
            this.page.send(this.page, Broadcast.BREADTH, domainEvent);
        }
    }

    @Nonnull
    @Override
    public Class<DomainEvent> subscribedToEventType() {
        return DomainEvent.class;
    }

}
