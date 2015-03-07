package promolo.wicket.core.ui.page;

import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;

import promolo.wicket.core.ui.component.HideEmptyFeedbackPanel;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class NotFoundPage extends WebPage {

    public NotFoundPage() {
        super();
        FencedFeedbackPanel pageFeedbackPanel = new FencedFeedbackPanel("pageFeedbackPanel");
        pageFeedbackPanel.add(new HideEmptyFeedbackPanel());
        add(pageFeedbackPanel);
    }

}
