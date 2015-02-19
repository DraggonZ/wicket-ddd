package promolo.wicket;

import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.account.instractructure.wicket.AccountEditorPanel;

public class HomePage extends WebPage {

    public HomePage(PageParameters parameters) {
        super(parameters);

        FencedFeedbackPanel pageFeedbackPanel = new FencedFeedbackPanel("pageFeedbackPanel") {

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(anyMessage());
            }

        };
        pageFeedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(pageFeedbackPanel);

        add(new AccountEditorPanel("accountEditorPanel", new Model<>()));
    }

}
