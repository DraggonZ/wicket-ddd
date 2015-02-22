package promolo.wicket.account.iface;

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

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountPresenter(@Nonnull AccountView accountView, @Nonnull String accountId) {
        this.accountView = accountView;
        this.accountId = accountId;
        NonContextual.of(AccountPresenter.class).inject(this);
    }

    public void onChangeAccountTitle(@Nonnull ChangeAccountPersonCommand command) {
        this.applicationCommandExecutor.execute(command);
        this.accountView.accountTitleChanged();
    }

    @Nonnull
    private String accountId() {
        return this.accountId;
    }

}
