package promolo.wicket;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;

import promolo.wicket.account.instractructure.presentation.AccountLayout;
import promolo.wicket.core.ui.application.BootstrapHeadContributor;
import promolo.wicket.core.ui.application.ConcurrencyViolationPostProcessor;
import promolo.wicket.core.ui.application.PresenterInstancePostProcessor;
import promolo.wicket.core.ui.notification.DomainEventNotificationListenerCollection;
import promolo.wicket.core.ui.notification.ViewDomainEventListenerProcessor;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {

    @Inject
    private DomainEventNotificationListenerCollection domainEventNotificationListenerCollection;

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
        getMarkupSettings().setStripWicketTags(true);
        getComponentInitializationListeners().add(new PresenterInstancePostProcessor());
        getRequestCycleListeners().add(new PageRequestHandlerTracker()); // должен быть первым
        getRequestCycleListeners().add(new ConcurrencyViolationPostProcessor());
        getRequestCycleListeners()
                .add(new ViewDomainEventListenerProcessor(this.domainEventNotificationListenerCollection)); // должен быть последним
        getHeaderContributorListenerCollection().add(new BootstrapHeadContributor(this));
        mountPage("account-layout.view", AccountLayout.class);
    }

}
