package promolo.wicket;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import promolo.wicket.core.domain.DomainEventPublisherCleaner;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {

    @Inject
    private DomainEventPublisherCleaner domainEventPublisherCleaner;

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        new BeanValidationConfiguration().configure(this);
        new CdiConfiguration().setPropagation(ConversationPropagation.NONE).configure(this);
        getHeaderContributorListenerCollection().add(new IHeaderContributor() {

            @Override
            public void renderHead(IHeaderResponse response) {
                Bootstrap.renderHeadPlain(response);
            }

        });
        getRequestCycleListeners().add(new AbstractRequestCycleListener() {

            @Override
            public void onDetach(RequestCycle cycle) {
                super.onDetach(cycle);
                WicketApplication.this.domainEventPublisherCleaner.cleanup();
            }

        });
    }

}
