package promolo.wicket.account.instractructure.presentation;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
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

        add(new BootstrapFeedbackPanel("errorFeedbackWrapper", BootstrapFeedbackKind.ERROR).add(RefreshOnAjaxBehavior.INSTANCE));
        add(new BootstrapFeedbackPanel("warningFeedbackWrapper", BootstrapFeedbackKind.WARNING).add(RefreshOnAjaxBehavior.INSTANCE));
        add(new BootstrapFeedbackPanel("successFeedbackWrapper", BootstrapFeedbackKind.SUCCESS).add(RefreshOnAjaxBehavior.INSTANCE));

        add(new AccountEditorPanel("accountEditorPanel"));
        add(new AccountListPanel("accountListPanel"));
        add(new AccountToolbarPanel("accountToolbarPanel"));
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }

}
