package promolo.wicket.account.instractructure.wicket;

import static promolo.wicket.core.ui.model.Bindgen.*;

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
import promolo.wicket.account.application.ChangeAccountPersonCommandBinding;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.ui.AccountPresenter;
import promolo.wicket.account.ui.AccountView;
import promolo.wicket.core.ui.component.HideEmptyFeedbackPanelBehavior;
import promolo.wicket.core.ui.page.NotFoundPage;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends GenericPanel<String> implements AccountView {

    @Inject
    private AccountApplicationService accountApplicationService;

    private final AccountPresenter presenter;

    private final Form<ChangeAccountPersonCommand> form;

    public AccountEditorPanel(@Nonnull String id, @Nonnull String login) {
        super(id, Model.of(login));

        ChangeAccountPersonCommandBinding binding = new ChangeAccountPersonCommandBinding();

        this.presenter = new AccountPresenter(this, login);

        this.form = new Form<>("form", new CompoundPropertyModel<>(createCommand()));
        this.form.add(feedbackPanelFor("feedback", this.form));
        this.form.add(new Label("id"));
        this.form.add(new TextField<>("title", forBinding(binding.title())).add(new PropertyValidator<>()));
        this.form.add(new TextField<>("lastName", forBinding(binding.lastName())).add(new PropertyValidator<>()));
        this.form.add(new TextField<>("firstName", forBinding(binding.firstName())).add(new PropertyValidator<>()));
        this.form.add(new TextField<>("middleName", forBinding(binding.middleName())).add(new PropertyValidator<>()));
        this.form.add(new SubmitLink("save") {

            @Override
            public void onSubmit() {
                super.onSubmit();
                presenter().onChangeAccountPerson((ChangeAccountPersonCommand) getForm().getDefaultModelObject());
            }

        });

        add(this.form);
    }

    @Override
    public void accountPersonChanged() {
        this.form.setDefaultModelObject(createCommand());
        success("Операция выполнена успешна.");
    }

    @Nonnull
    private ChangeAccountPersonCommand createCommand() {
        Account account = accountApplicationService().findAccountById(getModelObject());
        if (account == null) {
            getSession().error("Учетная запись с ID " + getModelObject() + "не найдена.");
            throw new RestartResponseAtInterceptPageException(new NotFoundPage());
        }
        ChangeAccountPersonCommand command =
                new ChangeAccountPersonCommand(account.id(), account.person().title(), account.person().firstName(),
                        account.person().middleName(), account.person().lastName());
        command.setVersion(account.concurrencyVersion());
        return command;
    }

    @Nonnull
    private AccountApplicationService accountApplicationService() {
        return this.accountApplicationService;
    }

    @Nonnull
    private AccountPresenter presenter() {
        return this.presenter;
    }

    @Nonnull
    private static FencedFeedbackPanel feedbackPanelFor(@Nonnull String id, @Nonnull Form<?> form) {
        ErrorLevelFeedbackMessageFilter feedbackMessageFilter = new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR);
        FencedFeedbackPanel fencedFeedbackPanel = new FencedFeedbackPanel(id, form, feedbackMessageFilter);
        fencedFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        return fencedFeedbackPanel;
    }

}
