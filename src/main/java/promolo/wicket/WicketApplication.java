package promolo.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.core.request.handler.IComponentRequestHandler;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;

import promolo.wicket.account.instractructure.presentation.AccountLayout;
import promolo.wicket.core.ui.application.BootstrapHeadContributor;
import promolo.wicket.core.ui.application.ConcurrencyViolationPostProcessor;
import promolo.wicket.core.ui.notification.ViewDomainEventListener;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return AccountLayout.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        new BeanValidationConfiguration().configure(this);
        new CdiConfiguration().setPropagation(ConversationPropagation.NONE).configure(this);
        getResourceSettings().setResourcePollFrequency(null); // TODO на WildFly под Windows зависает время от времени
        getMarkupSettings().setStripWicketTags(true);
        getRequestCycleListeners().add(new PageRequestHandlerTracker());
        getRequestCycleListeners().add(new ConcurrencyViolationPostProcessor());
        getRequestCycleListeners().add(new AbstractRequestCycleListener() { // TODO должен быть последним?

            @Override
            public void onRequestHandlerScheduled(RequestCycle cycle, IRequestHandler handler) {
                super.onRequestHandlerScheduled(cycle, handler);
                if (handler instanceof IComponentRequestHandler) {
                    handleComponentRequest((IComponentRequestHandler) handler);
                }
                if (handler instanceof IPageRequestHandler) {
                    handlePageRequest((IPageRequestHandler) handler);
                }
            }

            private void handleComponentRequest(IComponentRequestHandler handler) {
                Page page = (Page) handler.getComponent().getPage();
                page.add(new ViewDomainEventListener());
            }

            private void handlePageRequest(IPageRequestHandler handler) {
                Page page = (Page) handler.getPage();
                page.add(new ViewDomainEventListener());
            }

        });
        getHeaderContributorListenerCollection().add(new BootstrapHeadContributor(this));
        mountPage("account-layout.view", AccountLayout.class);
    }

}
