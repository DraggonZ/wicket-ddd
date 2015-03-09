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

    private String selectedAccountId;

    @Inject
    private transient AccountListDataProvider accountListDataProvider;

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
        inject();
        return this.accountListDataProvider.listAllAccounts();
    }

    @CheckForNull
    public AccountRow getSelectedRowItem() {
        inject();
        return (getSelectedAccountId() == null ? null : this.accountListDataProvider.findById(getSelectedAccountId()));
    }

    private String getSelectedAccountId() {
        return this.selectedAccountId;
    }

    private void setSelectedAccountId(String selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
    }

    @Nonnull
    private AccountListView view() {
        return this.accountListView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }
}
