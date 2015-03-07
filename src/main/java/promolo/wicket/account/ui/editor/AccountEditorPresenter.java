package promolo.wicket.account.ui.editor;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.application.CreateAccountCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.ui.list.SelectAccount;
import promolo.wicket.account.ui.toolbar.AddAccount;
import promolo.wicket.account.ui.toolbar.RemoveAccount;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.domain.DomainEvent;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.EventCatcher;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPresenter implements Serializable {

    private final AccountEditorView accountEditorView;

    private AccountEditModel accountEditModel = new AccountEditModel();

    @Inject
    private transient AccountApplicationService accountApplicationService;

    @Inject
    private transient ApplicationCommandExecutor applicationCommandExecutor;

    public AccountEditorPresenter(@Nonnull AccountEditorView accountEditorView) {
        this.accountEditorView = accountEditorView;
    }

    public AccountEditModel getAccountEditModel() {
        return this.accountEditModel;
    }

    public void onAddAccount(@Nonnull AddAccount event) {
        setAccountEditModel(new AccountEditModel());
        view().showEditor(getAccountEditModel());
    }

    public void onRemoveAccount(@Nonnull RemoveAccount event) {
        setAccountEditModel(null);
        view().closeEditor();
    }

    public void onSelectAccount(@Nonnull SelectAccount event) {
        inject();
        loadAccountEditModel(event.accountRowItem().getId());
        if (getAccountEditModel() != null) {
            view().showEditor(getAccountEditModel());
        }
    }

    public void onSaveAccount(@Nonnull SaveAccount event) {
        if (getAccountEditModel() != null) {
            inject();

            EventCatcher<DomainEvent> domainEventEventCatcher = EventCatcher.of(DomainEvent.class);
            DomainEventPublisher.instance().subscribe(domainEventEventCatcher);

            AccountEditModel model = getAccountEditModel();
            if (model.getVersion() == null) {
                CreateAccountCommand command = new CreateAccountCommand(model.getId());
                command.setTitle(model.getTitle());
                command.setFirstName(model.getFirstName());
                command.setMiddleName(model.getMiddleName());
                command.setLastName(model.getLastName());
                this.applicationCommandExecutor.execute(command);
            } else {
                ChangeAccountPersonCommand command = new ChangeAccountPersonCommand(model.getId());
                command.setVersion(model.getVersion());
                command.setTitle(model.getTitle());
                command.setFirstName(model.getFirstName());
                command.setMiddleName(model.getMiddleName());
                command.setLastName(model.getLastName());
                this.applicationCommandExecutor.execute(command);
            }

            if (domainEventEventCatcher.catched()) {
                // TODO notify success
            }

            loadAccountEditModel(model.getId());
            view().showEditor(getAccountEditModel());
        }
    }

    private void loadAccountEditModel(String id) {
        Account account = this.accountApplicationService.findAccountById(id);
        setAccountEditModel(account == null ? null : new AccountEditModel(account));
    }

    private void setAccountEditModel(AccountEditModel accountEditModel) {
        this.accountEditModel = accountEditModel;
    }

    @Nonnull
    private AccountEditorView view() {
        return this.accountEditorView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }

}
