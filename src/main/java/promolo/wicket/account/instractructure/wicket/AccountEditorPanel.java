package promolo.wicket.account.instractructure.wicket;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.ui.component.HideEmptyFeedbackPanelBehavior;
import promolo.wicket.ui.page.NotFoundPage;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends GenericPanel<String> {

    @Inject
    private AccountApplicationService accountApplicationService;

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountEditorPanel(@Nonnull String id, @Nonnull String login) {
        super(id, Model.of(login));

        Form<ChangeAccountPersonCommand> form = new Form<>("form", new CompoundPropertyModel<>(createCommand()));

        ErrorLevelFeedbackMessageFilter feedbackMessageFilter = new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR);
        FencedFeedbackPanel fencedFeedbackPanel = new FencedFeedbackPanel("feedback", form, feedbackMessageFilter);
        fencedFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        form.add(fencedFeedbackPanel);

        form.add(new Label("id"));

        form.add(new TextField<String>("title").add(new PropertyValidator<>()));

        form.add(new TextField<String>("lastName").add(new PropertyValidator<>()));
        form.add(new TextField<String>("firstName").add(new PropertyValidator<>()));
        form.add(new TextField<String>("middleName").add(new PropertyValidator<>()));

        form.add(new SubmitLink("save") {

            @Override
            public void onSubmit() {
                super.onSubmit();
                Form<ChangeAccountPersonCommand> form = (Form<ChangeAccountPersonCommand>) getForm();
                applicationCommandExecutor().execute(form.getModelObject());
                getPage().success("Операция выполнена успешна.");
                form.setModelObject(createCommand());
            }

        });

        add(form);
    }

    @Nonnull
    private ChangeAccountPersonCommand createCommand() {
        Account account = accountApplicationService().findAccountById(getModelObject());
        if (account == null) {
            getSession().error("Учетная запись с ID " + getModelObject() + "не найдена.");
            throw new RestartResponseAtInterceptPageException(new NotFoundPage());
        }
        return new ChangeAccountPersonCommand(account.id(), account.person().title(), account.person().firstName(),
                account.person().middleName(), account.person().lastName());
    }

    @Nonnull
    private AccountApplicationService accountApplicationService() {
        return this.accountApplicationService;
    }

    @Nonnull
    private ApplicationCommandExecutor applicationCommandExecutor() {
        return this.applicationCommandExecutor;
    }

}
