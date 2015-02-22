package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.ChangeAccountPersonCommand;
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

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountPresenter(@Nonnull AccountView accountView, @Nonnull String accountId) {
        this.accountView = accountView;
        this.accountId = accountId;
        NonContextual.of(AccountPresenter.class).inject(this);
    }

    public void onChangeAccountPerson(@Nonnull ChangeAccountPersonCommand command) {
        this.applicationCommandExecutor.execute(command);
        view().accountPersonChanged();
    }

    public void onPersonNameUpdated(ChangeAccountPersonCommand command) {
        command.setTitle(PersonTitle.build(command.getFirstName(), command.getMiddleName(), command.getLastName()));
        view().accountTitleGenerated();
    }

    public void toggleTitleAutoGeneration() {
        this.titleAutoGenerationEnabled = !this.titleAutoGenerationEnabled;
        if (this.titleAutoGenerationEnabled) {
            view().titleAutoGeneratorEnabled();
        } else {
            view().titleAutoGeneratorDisabled();
        }
    }

    public boolean isTitleAutoGenerationEnabled() {
        return this.titleAutoGenerationEnabled;
    }

    @Nonnull
    private String accountId() {
        return this.accountId;
    }

    @Nonnull
    private AccountView view() {
        return this.accountView;
    }
}
