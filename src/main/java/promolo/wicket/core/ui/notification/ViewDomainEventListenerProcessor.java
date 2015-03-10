package promolo.wicket.core.ui.notification;

import javax.annotation.Nonnull;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.IComponentRequestHandler;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * TODO javadoc
 *
 * @author lexx
 * @see promolo.wicket.core.ui.notification.ViewDomainEventListener
 */
public class ViewDomainEventListenerProcessor extends AbstractRequestCycleListener {

    private final DomainEventNotificationListenerCollection domainEventNotificationListenerCollection;

    public ViewDomainEventListenerProcessor(@Nonnull DomainEventNotificationListenerCollection domainEventNotificationListenerCollection) {
        this.domainEventNotificationListenerCollection = domainEventNotificationListenerCollection;
    }

    @Override
    public void onRequestHandlerScheduled(RequestCycle cycle, IRequestHandler handler) {
        super.onRequestHandlerScheduled(cycle, handler);
        if (handler instanceof IComponentRequestHandler) {
            handleComponentRequest(cycle, (IComponentRequestHandler) handler);
        }
        if (handler instanceof IPageRequestHandler) {
            handlePageRequest(cycle, (IPageRequestHandler) handler);
        }
    }

    private void handleComponentRequest(@Nonnull RequestCycle cycle, @Nonnull IComponentRequestHandler handler) {
        Page page = (Page) handler.getComponent().getPage();
        page.add(new ViewDomainEventListener(domainEventNotificationListenerCollection()));
    }

    private void handlePageRequest(@Nonnull RequestCycle cycle, @Nonnull IPageRequestHandler handler) {
        Page page = (Page) handler.getPage();
        page.add(new ViewDomainEventListener(domainEventNotificationListenerCollection()));
    }

    @Nonnull
    private DomainEventNotificationListenerCollection domainEventNotificationListenerCollection() {
        return this.domainEventNotificationListenerCollection;
    }

}
