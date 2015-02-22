package promolo.wicket.account.instractructure.wicket;

import static promolo.wicket.core.ui.model.Bindgen.*;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
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
        this.form.setOutputMarkupId(true);

        this.form.add(feedbackPanelFor("feedback", this.form));
        this.form.add(new Label("id"));
        this.form.add(new TitleAutoGeneratorToggler("checkbox"));

        TextField<String> title = new TextField<>("title", forBinding(binding.title()));
        title.add(new PropertyValidator<>(), new TitleFieldEnabler());
        this.form.add(title);

        this.form.add(new TextField<>("lastName", forBinding(binding.lastName())).add(new PropertyValidator<>(), new TitleAutoUpdater()));
        this.form.add(new TextField<>("firstName", forBinding(binding.firstName())).add(new PropertyValidator<>(), new TitleAutoUpdater()));
        this.form.add(new TextField<>("middleName", forBinding(binding.middleName()))
                .add(new PropertyValidator<>(), new TitleAutoUpdater()));

        this.form.add(new SubmitLink("save") {

            @Override
            public void onSubmit() {
                super.onSubmit();
                presenter().onChangeAccountPerson(form().getModelObject());
            }

        });

        add(this.form);
    }

    @Override
    public void accountPersonChanged() {
        form().setModelObject(createCommand());
        success("Операция выполнена успешна.");
    }

    @Override
    public void accountTitleGenerated() {
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(form().get("title")); // FIXME
        }
    }

    @Override
    public void titleAutoGeneratorEnabled() {
        form().info("Наименование учетной записи будет сгенерировано автоматически");
        updateFormViaAjax();
    }

    @Override
    public void titleAutoGeneratorDisabled() {
        form().warn("Наименование учетной записи необходимо указать вручную");
        updateFormViaAjax();
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

    private void updateFormViaAjax() {
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(form());
        }
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
    private Form<ChangeAccountPersonCommand> form() {
        return this.form;
    }

    @Nonnull
    private static FencedFeedbackPanel feedbackPanelFor(@Nonnull String id, @Nonnull Form<?> form) {
        IFeedbackMessageFilter feedbackMessageFilter = new ContainerFeedbackMessageFilter(form);
        FencedFeedbackPanel fencedFeedbackPanel = new FencedFeedbackPanel(id, form, feedbackMessageFilter);
        fencedFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
        return fencedFeedbackPanel;
    }

    private class TitleAutoGeneratorToggler extends AjaxCheckBox {

        public TitleAutoGeneratorToggler(@Nonnull String id) {
            super(id, new TitleAutoGeneratorTogglerModel());
        }

        @Override
        protected void onUpdate(AjaxRequestTarget target) {
            presenter().toggleTitleAutoGeneration();
            target.add(form());
        }

    }

    private final class TitleAutoGeneratorTogglerModel extends LoadableDetachableModel<Boolean> {

        @Override
        protected Boolean load() {
            return presenter().isTitleAutoGenerationEnabled();
        }

    }

    private final class TitleFieldEnabler extends Behavior {

        public TitleFieldEnabler() {
            super();
        }

        @Override
        public void bind(Component component) {
            super.bind(component);
            component.setOutputMarkupId(true);
        }

        @Override
        public void onConfigure(Component component) {
            super.onConfigure(component);
            component.setEnabled(!presenter().isTitleAutoGenerationEnabled());
        }

    }

    private final class TitleAutoUpdater extends AjaxFormComponentUpdatingBehavior {

        public TitleAutoUpdater() {
            super("onchange");
        }

        @Override
        public boolean isEnabled(Component component) {
            return presenter().isTitleAutoGenerationEnabled();
        }

        @Override
        protected void onUpdate(AjaxRequestTarget target) {
            presenter().onPersonNameUpdated(form().getModelObject());
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);
            attributes.setChannel(new AjaxChannel("ChangeTitleChannel", AjaxChannel.Type.DROP));
        }

    }

}
