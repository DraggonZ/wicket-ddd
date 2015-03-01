package promolo.wicket.account.ui;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class AccountEditModelChanged {

    private final AccountEditModel accountEditModel;

    public AccountEditModelChanged(@Nonnull AccountEditModel accountEditModel) {
        this.accountEditModel = accountEditModel;
    }

    @Nonnull
    public AccountEditModel accountEditModel() {
        return this.accountEditModel;
    }

}
