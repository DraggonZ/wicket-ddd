package promolo.wicket.account.instractructure.wicket;

import static promolo.wicket.core.ui.model.Bindgen.*;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.cdi.NonContextual;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.application.ChangeAccountPersonCommandBinding;
import promolo.wicket.account.ui.AccountPresenter;
import promolo.wicket.account.ui.AccountView;
import promolo.wicket.core.ui.component.HideEmptyFeedbackPanelBehavior;
import promolo.wicket.core.ui.component.RefreshOnAjaxBehavior;
import promolo.wicket.core.ui.page.NotFoundPage;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Deprecated
public class AccountEditorPanel extends GenericPanel<String> implements AccountView {

    private final AccountPresenter presenter;

    private final Form<ChangeAccountPersonCommand> form;

    public AccountEditorPanel(@Nonnull String id, @Nonnull String login) {
        super(id, Model.of(login));

        ChangeAccountPersonCommandBinding binding = new ChangeAccountPersonCommandBinding();

        this.presenter = new AccountPresenter(this, login);
        NonContextual.of(AccountPresenter.class).inject(this.presenter); // TODO подумать над абстракцией

        this.form = new AccountForm("form", new CompoundPropertyModel<>(newCommand()));
        this.form.setOutputMarkupId(true);

        this.form.add(feedbackPanelFor("feedback", this.form));
        this.form.add(new Label("id", modelOf(binding.id())), new Label("version", modelOf(binding.version())));

        this.form.add(new TitleAutoGeneratorToggler("checkbox"));

        TextField<String> title = new TextField<>("title", modelOf(binding.title()));
        title.add(new PropertyValidator<>(), new TitleFieldEnabler());
        this.form.add(title);

        this.form.add(new TextField<>("lastName", modelOf(binding.lastName())).add(new PropertyValidator<>(), new TitleAutoUpdater()));
        this.form.add(new TextField<>("firstName", modelOf(binding.firstName())).add(new PropertyValidator<>(), new TitleAutoUpdater()));
        this.form.add(new TextField<>("middleName", modelOf(binding.middleName())).add(new PropertyValidator<>(), new TitleAutoUpdater()));

        this.form.add(new AjaxSubmitLink("save") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                presenter().onSaveAccountChanges();
            }

        });

        add(this.form);
    }

    @Override
    public void accountPersonChanged() {
        success("Операция выполнена успешно.");
        ajaxUpdate(form());
    }

    @Override
    public void accountTitleGenerated() {
        ajaxUpdate(title());
    }

    @Override
    public void titleAutoGeneratorStateChanged(boolean enabled) {
        ajaxUpdate(title());
    }

    @Nonnull
    private ChangeAccountPersonCommand newCommand() {
        ChangeAccountPersonCommand command = presenter().refreshModel();
        if (command == null) {
            getSession().error("Учетная запись с ID " + getModelObject() + "не найдена.");
            throw new RestartResponseAtInterceptPageException(new NotFoundPage());
        }
        return command;
    }

    private void ajaxUpdate(@Nonnull Component target) {
        AjaxRequestHandler ajaxRequestHandler = ajaxRequestHandler();
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(target);
        }
    }

    @CheckForNull
    private AjaxRequestHandler ajaxRequestHandler() {
        return getRequestCycle().find(AjaxRequestHandler.class);
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
    private FormComponent<?> title() {
        return (FormComponent<?>) form().get("title");
    }

    @Nonnull
    private FencedFeedbackPanel feedbackPanelFor(@Nonnull String id, @Nonnull Form<?> form) {
        IFeedbackMessageFilter feedbackMessageFilter = new ContainerFeedbackMessageFilter(form);
        FencedFeedbackPanel fencedFeedbackPanel = new FencedFeedbackPanel(id, form, feedbackMessageFilter);
        fencedFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior(), new RefreshOnAjaxBehavior());
        return fencedFeedbackPanel;
    }

    private final class AccountForm extends Form<ChangeAccountPersonCommand> implements IAjaxIndicatorAware {

        private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

        public AccountForm(@Nonnull String id, @Nonnull IModel<ChangeAccountPersonCommand> model) {
            super(id, model);
            add(this.indicatorAppender);
        }

        @Override
        public String getAjaxIndicatorMarkupId() {
            return this.indicatorAppender.getMarkupId();
        }

    }

    private final class TitleAutoGeneratorToggler extends AjaxCheckBox {

        public TitleAutoGeneratorToggler(@Nonnull String id) {
            super(id, new TitleAutoGeneratorTogglerModel());
        }

        @Override
        protected void onUpdate(AjaxRequestTarget target) {
            presenter().toggleTitleAutoGeneration();
        }

    }

    private final class TitleAutoGeneratorTogglerModel implements IModel<Boolean> {

        @Override
        public Boolean getObject() {
            return presenter().isTitleAutoGenerationEnabled();
        }

        @Override
        public void setObject(Boolean object) {
            // не используется
        }

        @Override
        public void detach() {
            // nop
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
        protected void onUpdate(AjaxRequestTarget target) {
            presenter().onPersonNameUpdated();
        }

    }

}
