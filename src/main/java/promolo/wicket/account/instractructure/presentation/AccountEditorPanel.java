package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.ui.editor.AccountEditModel;
import promolo.wicket.account.ui.editor.AccountEditModelBinding;
import promolo.wicket.account.ui.editor.AccountEditorPresenter;
import promolo.wicket.account.ui.editor.AccountEditorView;
import promolo.wicket.account.ui.editor.SaveAccount;
import promolo.wicket.core.ui.component.HideEmptyComponent;
import promolo.wicket.core.ui.component.ViewEventListener;
import promolo.wicket.core.ui.model.Bindgen;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends GenericPanel<AccountEditModel> implements AccountEditorView {

    private static final AjaxChannel SUBMIT_AJAX_CHANNEL = new AjaxChannel("AccountEditorChannel", AjaxChannel.Type.ACTIVE);

    private final AccountEditorPresenter presenter = new AccountEditorPresenter(this);

    public AccountEditorPanel(String id) {
        super(id, new Model<AccountEditModel>());

        setVersioned(false);
        setOutputMarkupId(true);

        add(new ViewEventListener(presenter()));

        WebMarkupContainer panelTitleWrapper = new WebMarkupContainer("panelTitleWrapper");
        panelTitleWrapper.setOutputMarkupId(true);
        panelTitleWrapper.add(new Label("panelTitle", new PanelTitleModel()).add(HideEmptyComponent.INSTANCE));
        panelTitleWrapper.add(new Label("versionLabel", new VersionModel()).add(HideEmptyComponent.INSTANCE));
        add(panelTitleWrapper);

        WebMarkupContainer selectAccountAdviceBanner = new WebMarkupContainer("selectAccountAdviceBanner");
        selectAccountAdviceBanner.add(new ShowSelectAccountAdvice());
        add(selectAccountAdviceBanner);

        AccountEditModelBinding binding = new AccountEditModelBinding();
        Form<AccountEditModel> form = new Form<>("form", new CompoundPropertyModel<>(getModel()));
        form.setOutputMarkupId(true);
        form.add(HideEmptyComponent.INSTANCE);

        TextField<String> idTextField = new TextField<>("id", Bindgen.modelOf(binding.id()));
        idTextField.add(new PropertyValidator<String>(), new DisableForExistingAccount());
        form.add(idTextField);

        TextField<String> titleTextField = new TextField<>("title", Bindgen.modelOf(binding.title()));
        titleTextField.add(new PropertyValidator<String>());
        form.add(titleTextField);

        TextField<String> firstNameTextField = new TextField<>("firstName", Bindgen.modelOf(binding.firstName()));
        firstNameTextField.add(new PropertyValidator<String>(), new NameFieldUpdatedHandler());
        form.add(firstNameTextField);

        TextField<String> middleNameTextField = new TextField<>("middleName", Bindgen.modelOf(binding.middleName()));
        middleNameTextField.add(new PropertyValidator<String>(), new NameFieldUpdatedHandler());
        form.add(middleNameTextField);

        TextField<String> lastNameTextField = new TextField<>("lastName", Bindgen.modelOf(binding.lastName()));
        lastNameTextField.add(new PropertyValidator<String>(), new NameFieldUpdatedHandler());
        form.add(lastNameTextField);

        form.add(new AjaxSubmitLink("save") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                send(getPage(), Broadcast.BREADTH, new SaveAccount());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(form);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.setChannel(SUBMIT_AJAX_CHANNEL);
            }

        });

        add(form);
    }

    @Override
    public void showEditor(@Nonnull AccountEditModel accountEditModel) {
        clearForm();
        setModelObject(accountEditModel);
        ajaxRefreshEditor();
    }

    @Override
    public void closeEditor() {
        setModelObject(null);
        ajaxRefreshEditor();
    }

    private void ajaxRefreshEditor() {
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(this);
        }
    }

    private void clearForm() {
        ((Form<?>) get("form")).clearInput();
    }

    @Nonnull
    private AccountEditorPresenter presenter() {
        return this.presenter;
    }

    private final class PanelTitleModel extends AbstractReadOnlyModel<String> {

        public PanelTitleModel() {
            super();
        }

        @Override
        public String getObject() {
            AccountEditModel accountEditModel = getModelObject();
            return (accountEditModel == null ? null : accountEditModel.getTitle());
        }

    }

    private final class VersionModel extends AbstractReadOnlyModel<Long> {

        @Override
        public Long getObject() {
            AccountEditModel accountEditModel = getModelObject();
            return (accountEditModel == null ? null : accountEditModel.getVersion());
        }

    }

    private final class ShowSelectAccountAdvice extends Behavior {

        public ShowSelectAccountAdvice() {
            super();
        }

        @Override
        public void onConfigure(Component component) {
            super.onConfigure(component);
            component.setVisible(getModelObject() == null);
        }

    }

    private final class DisableForExistingAccount extends Behavior {

        public DisableForExistingAccount() {
            super();
        }

        @Override
        public void onConfigure(Component component) {
            super.onConfigure(component);
            component.setEnabled(getModelObject().getId() == null);
        }

    }

    private final class NameFieldUpdatedHandler extends AjaxFormComponentUpdatingBehavior {

        public NameFieldUpdatedHandler() {
            super("onchange");
        }

        @Override
        protected void onUpdate(AjaxRequestTarget target) {
            // TODO нотификация Presenter
        }

    }

}
