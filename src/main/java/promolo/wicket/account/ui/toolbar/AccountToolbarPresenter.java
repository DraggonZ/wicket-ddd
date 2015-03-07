package promolo.wicket.account.ui.toolbar;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.RemoveAccountCommand;
import promolo.wicket.account.ui.list.AccountRow;
import promolo.wicket.account.ui.list.SelectAccount;
import promolo.wicket.core.application.ApplicationCommandExecutor;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountToolbarPresenter implements Serializable {

    @Inject
    private transient ApplicationCommandExecutor applicationCommandExecutor;

    private final AccountToolbarView accountToolbarView;

    private AccountRow selectedItem;

    public AccountToolbarPresenter(@Nonnull AccountToolbarView accountToolbarView) {
        this.accountToolbarView = accountToolbarView;
    }

    @CheckForNull
    public AccountRow getSelectedItem() {
        return this.selectedItem;
    }

    public void onSelectAccount(@Nonnull SelectAccount event) {
        if (!Objects.equals(getSelectedItem(), event.accountRowItem())) {
            setSelectedItem(event.accountRowItem());
            view().updateControlPanel();
        }
    }

    public void onRemoveAccount(@Nonnull RemoveAccount event) {
        inject();
        this.applicationCommandExecutor.execute(new RemoveAccountCommand(getSelectedItem().getId()));
        setSelectedItem(null);
    }

    private void setSelectedItem(AccountRow selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Nonnull
    private AccountToolbarView view() {
        return this.accountToolbarView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }

}
