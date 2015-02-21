package promolo.wicket.ui.page;

import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;

import promolo.wicket.ui.component.HideEmptyFeedbackPanelBehavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class NotFoundPage extends WebPage {

    public NotFoundPage() {
        super();
        FencedFeedbackPanel pageFeedbackPanel = new FencedFeedbackPanel("pageFeedbackPanel");
        pageFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        add(pageFeedbackPanel);
    }

}
