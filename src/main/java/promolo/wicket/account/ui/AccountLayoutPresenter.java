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

    public void onAccountRecordSelected(@Nonnull AccountRecordSelected event) {
        String id = event.accountRecord().getId();
        view().updateAccountSelector(id);
        view().showAccountEditor(id);
        view().updateControlPanel(id);
    }

    public void onNewAccountRequested(@Nonnull NewAccountRequested event) {
        view().newAccountEditor();
    }

    public void onAccountEditModelChanged(@Nonnull AccountEditModelChanged event) {
        String id = event.accountEditModel().getId();
        if (event.accountEditModel().getVersion() == null) {
            view().updateAccountSelector(id);
        }
        view().showAccountEditor(id);
        view().updateAccountList();
    }

    @Nonnull
    private AccountLayoutView view() {
        return this.view;
    }

}
