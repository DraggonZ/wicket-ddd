package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.ui.AccountEditModel;
import promolo.wicket.account.ui.AccountEditModelBinding;
import promolo.wicket.core.ui.component.HideEmptyComponent;
import promolo.wicket.core.ui.model.Bindgen;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends GenericPanel<AccountEditModel> {

    @Inject
    private AccountApplicationService accountApplicationService;

    public AccountEditorPanel(String id) {
        super(id, new Model<AccountEditModel>());

        setVersioned(false);
        setOutputMarkupId(true);

        WebMarkupContainer panelTitleWrapper = new WebMarkupContainer("panelTitleWrapper");
        panelTitleWrapper.setOutputMarkupId(true);
        panelTitleWrapper.add(new Label("panelTitle", new PanelTitleModel()).add(new HideEmptyComponent()));
        panelTitleWrapper.add(new Label("versionLabel", new VersionModel()).add(new HideEmptyComponent()));
        add(panelTitleWrapper);

        WebMarkupContainer selectAccountAdviceBanner = new WebMarkupContainer("selectAccountAdviceBanner");
        selectAccountAdviceBanner.add(new ShowSelectAccountAdvice());
        add(selectAccountAdviceBanner);

        AccountEditModelBinding binding = new AccountEditModelBinding();
        Form<AccountEditModel> form = new Form<>("form", new CompoundPropertyModel<>(getModel()));
        form.add(new HideEmptyComponent());

        TextField<String> idTextField = new TextField<>("id", Bindgen.modelOf(binding.id()));
        idTextField.add(new PropertyValidator<String>(), new DisableForExistingAccount());
        form.add(idTextField);

        TextField<String> titleTextField = new TextField<>("title", Bindgen.modelOf(binding.title()));
        titleTextField.add(new PropertyValidator<String>());
        form.add(titleTextField);

        TextField<String> firstNameTextField = new TextField<>("firstName", Bindgen.modelOf(binding.firstName()));
        firstNameTextField.add(new PropertyValidator<String>());
        form.add(firstNameTextField);

        TextField<String> middleNameTextField = new TextField<>("middleName", Bindgen.modelOf(binding.middleName()));
        middleNameTextField.add(new PropertyValidator<String>());
        form.add(middleNameTextField);

        TextField<String> lastNameTextField = new TextField<>("lastName", Bindgen.modelOf(binding.lastName()));
        lastNameTextField.add(new PropertyValidator<String>());
        form.add(lastNameTextField);

        add(form);
    }

    public void editAccount(@Nonnull String id) {
        Account account = accountApplicationService().findAccountById(id);
        setModelObject(account == null ? null : new AccountEditModel(account));
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(this);
        }
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setEnabled(getModelObject() != null);
    }

    @Nonnull
    private AccountApplicationService accountApplicationService() {
        return this.accountApplicationService;
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

}
