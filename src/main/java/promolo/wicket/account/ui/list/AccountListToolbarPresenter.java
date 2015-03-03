package promolo.wicket.account.ui.list;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;

import promolo.wicket.account.application.RemoveAccountCommand;
import promolo.wicket.account.domain.AccountRemoved;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.EventCatcher;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListToolbarPresenter implements Serializable {

    @Inject
    private transient ApplicationCommandExecutor applicationCommandExecutor;

    private final AccountListToolbarView accountListToolbarView;

    private AccountRow selectedItem;

    public AccountListToolbarPresenter(@Nonnull AccountListToolbarView accountListToolbarView) {
        this.accountListToolbarView = accountListToolbarView;
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
        EventCatcher<AccountRemoved> accountRemovedEventCatcher = EventCatcher.of(AccountRemoved.class);
        DomainEventPublisher.instance().subscribe(accountRemovedEventCatcher);
        this.applicationCommandExecutor.execute(new RemoveAccountCommand(getSelectedItem().getId()));
        if (accountRemovedEventCatcher.catched()) {
            view().notifyAccountWasRemoved(getSelectedItem());
        }
        setSelectedItem(null);
        view().updateControlPanel();
    }

    private void setSelectedItem(AccountRow selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Nonnull
    private AccountListToolbarView view() {
        return this.accountListToolbarView;
    }

    private void inject() {
        NonContextual.of(getClass()).inject(this);
    }

}
