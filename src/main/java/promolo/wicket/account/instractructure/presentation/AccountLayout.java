package promolo.wicket.account.instractructure.presentation;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackKind;
import promolo.wicket.core.ui.bootstrap.BootstrapFeedbackPanel;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayout extends WebPage {

    public AccountLayout(PageParameters parameters) {
        super(parameters);
        add(new BootstrapFeedbackPanel("errorFeedbackWrapper", BootstrapFeedbackKind.ERROR));
        add(new BootstrapFeedbackPanel("warningFeedbackWrapper", BootstrapFeedbackKind.WARNING));
        add(new BootstrapFeedbackPanel("successFeedbackWrapper", BootstrapFeedbackKind.SUCCESS));
        add(new AccountEditorPanel("accountEditorPanel"));
        add(new AccountListPanel("accountListPanel"));
        add(new AccountControlPanel("accountControlPanel"));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        success("Тест");
    }
    
}
