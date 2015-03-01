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

    private String selectedAccountId;

    public AccountLayoutPresenter(@Nonnull AccountLayoutView view) {
        this.view = view;
    }

    public void onAccountRecordSelected(@Nonnull AccountRecordSelected event) {
        setSelectedAccountId(event.accountRecord().getId());
        view().showAccountEditor(selectedAccountId());
        view().updateControlPanel(selectedAccountId());
    }

    public void onAccountEditModelChanged(@Nonnull AccountEditModelChanged event) {
        view().updateAccountList();
    }

    @Nonnull
    private AccountLayoutView view() {
        return this.view;
    }

    private String selectedAccountId() {
        return this.selectedAccountId;
    }

    private void setSelectedAccountId(String selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
    }
}
