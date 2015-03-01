package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountLayoutPresenter implements Serializable {

    private final AccountLayoutView view;

    public AccountLayoutPresenter(@Nonnull AccountLayoutView view) {
        this.view = view;
    }

    public void onAccountRecordSelected(@Nonnull AccountRecordSelected selected) {
        selected.toString(); // FIXME
    }

    @Nonnull
    private AccountLayoutView view() {
        return this.view;

    }

}
