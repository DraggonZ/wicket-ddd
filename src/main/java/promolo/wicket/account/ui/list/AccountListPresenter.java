package promolo.wicket.account.ui.list;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListPresenter implements Serializable {

    private final AccountListView accountListView;

    private String selectedAccountId;

    @Inject
    private AccountListDataProvider accountListDataProvider;

    public AccountListPresenter(@Nonnull AccountListView accountListView) {
        this.accountListView = accountListView;
    }

    public void onSelectAccount(@Nonnull SelectAccount event) {
        if (!Objects.equals(getSelectedAccountId(), event.id())) {
            setSelectedAccountId(event.id());
            view().updateAccountList();
        }
    }

    @Nonnull
    public List<AccountRow> getAccountListModel() {
        return this.accountListDataProvider.listAllAccounts();
    }

    @CheckForNull
    public String getSelectedAccountId() {
        return this.selectedAccountId;
    }

    private void setSelectedAccountId(String selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
    }

    @Nonnull
    private AccountListView view() {
        return this.accountListView;
    }

}
