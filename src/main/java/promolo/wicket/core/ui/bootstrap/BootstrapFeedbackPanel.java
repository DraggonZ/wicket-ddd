package promolo.wicket.core.ui.bootstrap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class BootstrapFeedbackPanel extends Panel {

    private final FencedFeedbackPanel feedbackPanel;

    public BootstrapFeedbackPanel(String id, BootstrapFeedbackKind bootstrapFeedbackKind) {
        super(id);
        setOutputMarkupPlaceholderTag(true);

        this.feedbackPanel = new FencedFeedbackPanel("feedbackPanel", null, bootstrapFeedbackKind);
        this.feedbackPanel.setRenderBodyOnly(true);

        WebMarkupContainer alertBlock = new WebMarkupContainer("alertBlock");
        alertBlock.add(new AlertClass(bootstrapFeedbackKind));
        alertBlock.add(this.feedbackPanel);

        add(new HideEmptyFeedback(this.feedbackPanel));
        add(alertBlock);
    }

    public final boolean anyMessage() {
        return this.feedbackPanel.anyMessage();
    }

    private static final class HideEmptyFeedback extends Behavior {

        private final FeedbackPanel feedbackPanel;

        public HideEmptyFeedback(FeedbackPanel feedbackPanel) {
            this.feedbackPanel = feedbackPanel;
        }

        @Override
        public void onConfigure(Component component) {
            super.onConfigure(component);
            component.setVisible(feedbackPanel.anyMessage());
        }

    }

    private static final class AlertClass extends Behavior {

        private final BootstrapFeedbackKind bootstrapFeedbackKind;

        public AlertClass(BootstrapFeedbackKind bootstrapFeedbackKind) {
            this.bootstrapFeedbackKind = bootstrapFeedbackKind;
        }

        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            super.onComponentTag(component, tag);
            tag.append("class", this.bootstrapFeedbackKind.getAlertClass(), " ");
        }

    }
}
