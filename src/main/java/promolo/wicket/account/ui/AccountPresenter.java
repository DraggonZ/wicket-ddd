package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountPersonChanged;
import promolo.wicket.account.domain.PersonTitle;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.EventCatcher;

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
        ChangeAccountPersonCommand instance = getCommand();
        setCommand(null);
        Account account = this.accountApplicationService.findAccountById(accountId());
        if (account != null) {
            if (instance == null) {
                instance = new ChangeAccountPersonCommand(accountId());
            }
            instance.setVersion(account.concurrencyVersion());
            instance.setTitle(account.person().title());
            instance.setFirstName(account.person().firstName());
            instance.setMiddleName(account.person().middleName());
            instance.setLastName(account.person().lastName());
            setCommand(instance);
        }
        return getCommand();
    }

    public void onPersonNameUpdated() {
        if (getCommand() != null && isTitleAutoGenerationEnabled()) {
            autoGenerateTitle(getCommand());
            view().accountTitleGenerated();
        }
    }

    public void onSaveAccountChanges() {
        if (getCommand() != null) {
            EventCatcher eventCatcher = EventCatcher.of(AccountPersonChanged.class);
            DomainEventPublisher.instance().subscribe(eventCatcher);
            this.applicationCommandExecutor.execute(getCommand());
            if (eventCatcher.cached()) {
                setTitleAutoGenerationEnabled(false);
                refreshModel();
                view().accountPersonChanged();
            } else {
                // TODO ни чего не изменилось
            }
        }
    }

    public void toggleTitleAutoGeneration() {
        setTitleAutoGenerationEnabled(!isTitleAutoGenerationEnabled());
        autoGenerateTitle(getCommand());
        view().titleAutoGeneratorStateChanged(isTitleAutoGenerationEnabled());
    }

    public boolean isTitleAutoGenerationEnabled() {
        return this.titleAutoGenerationEnabled;
    }

    @Nonnull
    private String accountId() {
        return this.accountId;
    }

    @CheckForNull
    /* package */ ChangeAccountPersonCommand getCommand() {
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
