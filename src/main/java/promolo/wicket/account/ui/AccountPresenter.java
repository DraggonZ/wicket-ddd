package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.PersonTitle;
import promolo.wicket.core.application.ApplicationCommandExecutor;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountPresenter implements Serializable {

    private final AccountView accountView;

    private final String accountId;

    private boolean titleAutoGenerationEnabled = false;

    private ChangeAccountPersonCommand command;

    private AccountApplicationService accountApplicationService;

    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountPresenter(@Nonnull AccountView accountView, @Nonnull String accountId) {
        this.accountView = accountView;
        this.accountId = accountId;
        NonContextual.of(AccountPresenter.class).inject(this);
    }

    @Inject
    public void setAccountApplicationService(@Nonnull AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @Inject
    public void setApplicationCommandExecutor(@Nonnull ApplicationCommandExecutor applicationCommandExecutor) {
        this.applicationCommandExecutor = applicationCommandExecutor;
    }

    @CheckForNull
    public ChangeAccountPersonCommand refreshModel() {
        setTitleAutoGenerationEnabled(false);
        Account account = this.accountApplicationService.findAccountById(accountId());
        if (account == null) {
            setCommand(null);
            return null;
        } else {
            ChangeAccountPersonCommand changeAccountPersonCommand = new ChangeAccountPersonCommand(accountId());
            changeAccountPersonCommand.setVersion(account.concurrencyVersion());
            changeAccountPersonCommand.setTitle(account.person().title());
            changeAccountPersonCommand.setFirstName(account.person().firstName());
            changeAccountPersonCommand.setMiddleName(account.person().middleName());
            changeAccountPersonCommand.setLastName(account.person().lastName());
            setCommand(changeAccountPersonCommand);
            return changeAccountPersonCommand;
        }
    }

    public void onSaveAccountChanges() {
        if (command() != null) {
            this.applicationCommandExecutor.execute(command());
            refreshModel();
            view().accountPersonChanged();
        }
    }

    public void onPersonNameUpdated() {
        if (command() != null && isTitleAutoGenerationEnabled()) {
            autoGenerateTitle(command());
            view().accountTitleGenerated();
        }
    }

    public void toggleTitleAutoGeneration() {
        setTitleAutoGenerationEnabled(!isTitleAutoGenerationEnabled());
        autoGenerateTitle(command());
        view().titleAutoGeneratorStateChanged(isTitleAutoGenerationEnabled());
    }

    public boolean isTitleAutoGenerationEnabled() {
        return this.titleAutoGenerationEnabled;
    }

    @Nonnull
    private String accountId() {
        return this.accountId;
    }

    private ChangeAccountPersonCommand command() {
        return this.command;
    }

    private void setCommand(ChangeAccountPersonCommand command) {
        this.command = command;
    }

    private void setTitleAutoGenerationEnabled(boolean titleAutoGenerationEnabled) {
        this.titleAutoGenerationEnabled = titleAutoGenerationEnabled;
    }

    @Nonnull
    private AccountView view() {
        return this.accountView;
    }

    private static void autoGenerateTitle(@Nonnull ChangeAccountPersonCommand command) {
        command.setTitle(PersonTitle.build(command.getFirstName(), command.getMiddleName(), command.getLastName()));
    }

}
