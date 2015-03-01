package promolo.wicket.core.ui.bootstrap;

import org.apache.wicket.feedback.FeedbackMessage;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public enum BootstrapFeedbackKind implements BootstrapFeedbackMessageFilter {
    ERROR {
        @Override
        public String getAlertClass() {
            return "alert-danger";
        }

        @Override
        public boolean accept(FeedbackMessage message) {
            return (message.getLevel() >= FeedbackMessage.ERROR);
        }
    },
    WARNING {
        @Override
        public String getAlertClass() {
            return "alert-warning";
        }

        @Override
        public boolean accept(FeedbackMessage message) {
            return (message.getLevel() == FeedbackMessage.WARNING);
        }
    },
    SUCCESS {
        @Override
        public String getAlertClass() {
            return "alert-success";
        }

        @Override
        public boolean accept(FeedbackMessage message) {
            return (message.getLevel() > FeedbackMessage.UNDEFINED && message.getLevel() <= FeedbackMessage.SUCCESS);
        }
    }
}
