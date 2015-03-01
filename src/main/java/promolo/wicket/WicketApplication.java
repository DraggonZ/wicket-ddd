package promolo.wicket;

import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.wicket.Session;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;

import promolo.wicket.core.domain.ConcurrencyViolationException;
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

        getResourceSettings().setResourcePollFrequency(null); // TODO на WildFly под Windows зависает время от времени
        getMarkupSettings().setStripWicketTags(true);
        getRequestCycleListeners().add(new PageRequestHandlerTracker());
        getRequestCycleListeners().add(new AbstractRequestCycleListener() {

            @Override
            public IRequestHandler onException(RequestCycle cycle, Exception ex) {
                @SuppressWarnings("ThrowableResultOfMethodCallIgnored") Throwable rootCause = ExceptionUtils.getRootCause(ex);
                if (rootCause instanceof ConcurrencyViolationException) {
                    if (Session.exists()) {
                        Session.get().warn("Другой пользователь уже изменил данный объект.");
                    }
                    IPageRequestHandler pageRequestHandler = PageRequestHandlerTracker.getFirstHandler(cycle);
                    if (pageRequestHandler != null) {
                        return new RenderPageRequestHandler(new PageProvider(pageRequestHandler.getPage()));
                    }
                }
                return null;
            }

            @Override
            public void onDetach(RequestCycle cycle) {
                super.onDetach(cycle);
                WicketApplication.this.domainEventPublisherCleaner.cleanup();
            }

        });
        getHeaderContributorListenerCollection().add(new IHeaderContributor() {

            @Override
            public void renderHead(IHeaderResponse response) {
                response.render(JavaScriptHeaderItem.forReference(getJavaScriptLibrarySettings().getJQueryReference()));
                response.render(JavaScriptHeaderItem.forUrl("js/bootstrap.js", "bootstrap-js"));
            }

        });
        mountPage("account-layout.view", AccountLayout.class);
    }

}
