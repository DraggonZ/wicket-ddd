package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackKind;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackPanel;
import promolo.wicket.core.ui.component.RefreshOnAjaxBehavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayout extends WebPage implements IAjaxIndicatorAware {

    private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

    public AccountLayout(PageParameters parameters) {
        super(parameters);

        setVersioned(false);

        WebMarkupContainer ajaxBusyIndicator = new WebMarkupContainer("ajaxBusyIndicator");
        ajaxBusyIndicator.add(this.indicatorAppender);
        add(ajaxBusyIndicator);

        add(new BootstrapFeedbackPanel("errorFeedbackWrapper", BootstrapFeedbackKind.ERROR).add(RefreshIfAnyMessage.INSTANCE));
        add(new BootstrapFeedbackPanel("warningFeedbackWrapper", BootstrapFeedbackKind.WARNING).add(RefreshIfAnyMessage.INSTANCE));
        add(new BootstrapFeedbackPanel("successFeedbackWrapper", BootstrapFeedbackKind.SUCCESS).add(RefreshIfAnyMessage.INSTANCE));

        add(new AccountEditorPanel("accountEditorPanel"));
        add(new AccountListPanel("accountListPanel"));
        add(new AccountToolbarPanel("accountToolbarPanel"));
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }

    private static final class RefreshIfAnyMessage extends RefreshOnAjaxBehavior {

        public static final RefreshIfAnyMessage INSTANCE = new RefreshIfAnyMessage();

        @Override
        protected boolean acceptEvent(@Nonnull Component component, @Nonnull IEvent<?> event) {
            return ((BootstrapFeedbackPanel) component).anyMessage();
        }

        private RefreshIfAnyMessage() {
            super();
        }

    }

}
