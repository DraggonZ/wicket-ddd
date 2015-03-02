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
        view().showAccountEditor(id);
        view().updateAccountSelector(id);
        view().updateControlPanel(event.accountRecord().getId());
    }

    public void onNewAccountRequested(@Nonnull NewAccountRequested event) {
        view().newAccountEditor();
        view().updateAccountSelector(null);
        view().updateControlPanel(null);
    }

    public void onAccountRemoveRequested(@Nonnull AccountRemoveRequested event) {
        view().showAccountEditor(null);
        view().updateAccountSelector(null);
        view().updateAccountList();
        view().updateControlPanel(null);
    }

    public void onAccountEditModelChanged(@Nonnull AccountEditModelChanged event) {
        String id = event.accountEditModel().getId();
        boolean isNew = (event.accountEditModel().getVersion() == null);
        if (isNew) {
            view().updateAccountSelector(id);
            view().updateControlPanel(event.accountEditModel().getId());
        }
        view().showAccountEditor(id);
        view().updateAccountList();
    }

    @Nonnull
    private AccountLayoutView view() {
        return this.view;
    }

}
