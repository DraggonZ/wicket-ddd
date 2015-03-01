package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.account.ui.AccountLayoutPresenter;
import promolo.wicket.account.ui.AccountLayoutView;
import promolo.wicket.account.ui.AccountRecordSelected;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackKind;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackPanel;
import promolo.wicket.core.ui.component.ViewEventForwardingBehavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayout extends WebPage implements IAjaxIndicatorAware, AccountLayoutView {

    private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

    private final AccountLayoutPresenter accountLayoutPresenter = new AccountLayoutPresenter(this);

    public AccountLayout(PageParameters parameters) {
        super(parameters);

        add(new ViewEventForwardingBehavior(accountLayoutPresenter()));

        WebMarkupContainer ajaxBusyIndicator = new WebMarkupContainer("ajaxBusyIndicator");
        ajaxBusyIndicator.add(this.indicatorAppender);
        add(ajaxBusyIndicator);

        add(new BootstrapFeedbackPanel("errorFeedbackWrapper", BootstrapFeedbackKind.ERROR));
        add(new BootstrapFeedbackPanel("warningFeedbackWrapper", BootstrapFeedbackKind.WARNING));
        add(new BootstrapFeedbackPanel("successFeedbackWrapper", BootstrapFeedbackKind.SUCCESS));

        add(new AccountEditorPanel("accountEditorPanel"));
        add(new AccountListPanel("accountListPanel"));
        add(new AccountControlPanel("accountControlPanel"));
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }

    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if (event.getPayload() instanceof AccountRecordSelected) {
            event.getPayload().toString();
        }
    }

    @Nonnull
    private AccountLayoutPresenter accountLayoutPresenter() {
        return this.accountLayoutPresenter;

    }

}
