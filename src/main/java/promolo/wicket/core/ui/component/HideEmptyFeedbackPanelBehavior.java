package promolo.wicket.core.ui.component;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class HideEmptyFeedbackPanelBehavior extends Behavior {

    public HideEmptyFeedbackPanelBehavior() {
        super();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        if (component instanceof FeedbackPanel) {
            component.setOutputMarkupPlaceholderTag(true);
        } else {
            throw new IllegalStateException("ожидается панель FeedbackPanel");
        }
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        FeedbackPanel feedbackPanel = (FeedbackPanel) component;
        component.setVisible(feedbackPanel.anyMessage());
    }

}
