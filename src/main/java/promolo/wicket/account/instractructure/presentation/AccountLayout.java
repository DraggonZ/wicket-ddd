package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.account.ui.AccountLayoutPresenter;
import promolo.wicket.account.ui.AccountLayoutView;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackKind;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackPanel;
import promolo.wicket.core.ui.component.RefreshOnAjaxBehavior;
import promolo.wicket.core.ui.component.ViewEventForwardingBehavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayout extends WebPage implements AccountLayoutView, IAjaxIndicatorAware {

    private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

    private final AccountLayoutPresenter accountLayoutPresenter = new AccountLayoutPresenter(this);

    private final AccountEditorPanel accountEditorPanel = new AccountEditorPanel("accountEditorPanel");

    private final AccountListPanel accountListPanel = new AccountListPanel("accountListPanel");

    private final AccountControlPanel accountControlPanel = new AccountControlPanel("accountControlPanel");

    public AccountLayout(PageParameters parameters) {
        super(parameters);

        setVersioned(false);

        add(new ViewEventForwardingBehavior(accountLayoutPresenter()));

        WebMarkupContainer ajaxBusyIndicator = new WebMarkupContainer("ajaxBusyIndicator");
        ajaxBusyIndicator.add(this.indicatorAppender);
        add(ajaxBusyIndicator);

        add(new BootstrapFeedbackPanel("errorFeedbackWrapper", BootstrapFeedbackKind.ERROR).add(RefreshOnAjaxBehavior.INSTANCE));
        add(new BootstrapFeedbackPanel("warningFeedbackWrapper", BootstrapFeedbackKind.WARNING).add(RefreshOnAjaxBehavior.INSTANCE));
        add(new BootstrapFeedbackPanel("successFeedbackWrapper", BootstrapFeedbackKind.SUCCESS).add(RefreshOnAjaxBehavior.INSTANCE));

        add(this.accountEditorPanel);
        add(this.accountListPanel);
        add(this.accountControlPanel);
    }

    @Override
    public void newAccountEditor() {
        this.accountEditorPanel.newAccount();
    }

    @Override
    public void showAccountEditor(@Nonnull String id) {
        this.accountEditorPanel.editAccount(id);
    }

    @Override
    public void updateAccountList() {
        this.accountListPanel.refresh();
    }

    @Override
    public void updateControlPanel(String selectedAccountId) {
        this.accountControlPanel.refresh(selectedAccountId);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }

    @Nonnull
    private AccountLayoutPresenter accountLayoutPresenter() {
        return this.accountLayoutPresenter;
    }
}
