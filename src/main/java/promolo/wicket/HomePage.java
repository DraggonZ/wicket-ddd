package promolo.wicket;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.instractructure.wicket.AccountEditorPanel;
import promolo.wicket.core.ui.component.HideEmptyFeedbackPanelBehavior;

public class HomePage extends WebPage {

    @Inject
    private AccountApplicationService accountApplicationService;

    public HomePage(PageParameters parameters) {
        super(parameters);

        if (parameters.get("id").isEmpty()) {
            throw new RestartResponseException(HomePage.class, new PageParameters().add("id", "jdou"));
        }

        FencedFeedbackPanel pageFeedbackPanel = new FencedFeedbackPanel("pageFeedbackPanel");
        pageFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        add(pageFeedbackPanel);

        add(new AccountEditorPanel("accountEditorPanel", parameters.get("id").toString()));
    }

}
