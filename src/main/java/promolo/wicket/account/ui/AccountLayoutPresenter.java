package promolo.wicket.account.ui;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayoutPresenter {

    private final AccountLayoutView view;

    public AccountLayoutPresenter(@Nonnull AccountLayoutView view) {
        this.view = view;
    }

    @Nonnull
    private AccountLayoutView view() {
        return this.view;

    }

}
