package promolo.wicket.account.ui.toolbar;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.RemoveAccountCommand;
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

    private String selectedAccountId;

    public AccountToolbarPresenter(@Nonnull AccountToolbarView accountToolbarView) {
        this.accountToolbarView = accountToolbarView;
    }

    public void onSelectAccount(@Nonnull SelectAccount event) {
        if (!Objects.equals(getSelectedAccountId(), event.id())) {
            setSelectedAccountId(event.id());
            view().updateControlPanel();
        }
    }

    public void onRemoveAccount(@Nonnull RemoveAccount event) {
        inject();
        if (Objects.equals(getSelectedAccountId(), event.id())) {
            this.applicationCommandExecutor.execute(new RemoveAccountCommand(getSelectedAccountId()));
            setSelectedAccountId(null);
        }
    }

    @CheckForNull
    public String getSelectedAccountId() {
        return this.selectedAccountId;
    }

    private void setSelectedAccountId(String selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
    }

    @Nonnull
    private AccountToolbarView view() {
        return this.accountToolbarView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }

}
