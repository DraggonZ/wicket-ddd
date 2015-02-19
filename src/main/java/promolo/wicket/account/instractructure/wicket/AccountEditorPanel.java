package promolo.wicket.account.instractructure.wicket;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import promolo.wicket.account.application.ChangeAccountTitleCommand;
import promolo.wicket.component.HideEmptyFeedbackPanelBehavior;
import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends Panel {

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountEditorPanel(String id, IModel<?> model) {
        super(id, model);

        Form<ChangeAccountTitleCommand> form = new Form<>("form", new CompoundPropertyModel<>(new ChangeAccountTitleCommand("jdou")));
        ErrorLevelFeedbackMessageFilter feedbackMessageFilter = new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR);
        FencedFeedbackPanel fencedFeedbackPanel = new FencedFeedbackPanel("feedback", form, feedbackMessageFilter);
        fencedFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        form.add(fencedFeedbackPanel);
        form.add(new Label("id"));
        form.add(new TextField<String>("title").add(new PropertyValidator<>()));
        form.add(new SubmitLink("save") {

            @Override
            public void onSubmit() {
                super.onSubmit();
                applicationCommandExecutor().execute((ApplicationCommand) getForm().getModelObject());
                getPage().success("Операция выполнена успешна.");
            }

        });
        add(form);
    }

    @Nonnull
    private ApplicationCommandExecutor applicationCommandExecutor() {
        return this.applicationCommandExecutor;
    }

}
