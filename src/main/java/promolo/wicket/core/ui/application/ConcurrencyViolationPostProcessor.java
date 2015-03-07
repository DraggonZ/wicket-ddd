package promolo.wicket.core.ui.application;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import promolo.wicket.core.domain.ConcurrencyViolationException;

/**
 * @author lexx
 */
public class ConcurrencyViolationPostProcessor extends AbstractRequestCycleListener {

    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored") Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof ConcurrencyViolationException) {
            if (Session.exists()) {
                Session.get().warn("Другой пользователь уже изменил данный объект.");
            }
            IPageRequestHandler pageRequestHandler = PageRequestHandlerTracker.getFirstHandler(cycle);
            if (pageRequestHandler != null) {
                IRequestablePage page = pageRequestHandler.getPage();
                return (page == null ? null : new RenderPageRequestHandler(new PageProvider(page)));
            }
        }
        return null;
    }

}
