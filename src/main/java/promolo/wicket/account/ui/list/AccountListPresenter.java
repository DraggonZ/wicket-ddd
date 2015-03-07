package promolo.wicket.account.ui.list;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListPresenter implements Serializable {

    private final AccountListView accountListView;

    private AccountRow selectedAccountRow;

    @Inject
    private transient AccountListDataProvider accountListDataProvider;

    public AccountListPresenter(@Nonnull AccountListView accountListView) {
        this.accountListView = accountListView;
    }

    public void onSelectAccount(@Nonnull SelectAccount event) {
        if (!Objects.equals(getSelectedRowItem(), event.accountRowItem())) {
            setSelectedAccountRow(event.accountRowItem());
            view().updateAccountList();
        }
    }

    @Nonnull
    public List<AccountRow> getAccountListModel() {
        inject();
        return this.accountListDataProvider.listAllAccounts();
    }

    @CheckForNull
    public AccountRow getSelectedRowItem() {
        return this.selectedAccountRow;
    }

    private void setSelectedAccountRow(AccountRow selectedAccountRow) {
        this.selectedAccountRow = selectedAccountRow;
    }

    @Nonnull
    private AccountListView view() {
        return this.accountListView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }
}
