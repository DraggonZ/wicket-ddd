package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.ChangeAccountPersonCommand;
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
        if (isTitleAutoGenerationEnabled()) {
            command.setTitle(null);
        }
        this.applicationCommandExecutor.execute(command);
        view().accountPersonChanged();
    }

    public void toggleTitleAutoGeneration() {
        this.titleAutoGenerationEnabled = !this.titleAutoGenerationEnabled;
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
