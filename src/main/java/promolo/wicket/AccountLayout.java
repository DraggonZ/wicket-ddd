package promolo.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
    }

}
