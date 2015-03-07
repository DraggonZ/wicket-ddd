package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;

import promolo.wicket.account.ui.list.AccountRow;
import promolo.wicket.account.ui.toolbar.AccountToolbarPresenter;
import promolo.wicket.account.ui.toolbar.AccountToolbarView;
import promolo.wicket.account.ui.toolbar.AddAccount;
import promolo.wicket.account.ui.toolbar.RemoveAccount;
import promolo.wicket.core.ui.component.DisableEmptyComponent;
import promolo.wicket.core.ui.component.ViewEventForwardingBehavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountToolbarPanel extends Panel implements AccountToolbarView {

    private final AccountToolbarPresenter presenter = new AccountToolbarPresenter(this);

    public AccountToolbarPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new ViewEventForwardingBehavior(presenter()));
        AjaxLink<Void> addLink = new AjaxLink<Void>("add") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new AddAccount());
            }

        };
        add(addLink);

        AjaxLink<AccountRow> removeLink = new AjaxLink<AccountRow>("remove", new SelectedItemModel()) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new RemoveAccount(getModelObject().getId()));
            }

        };
        removeLink.add(DisableEmptyComponent.INSTANCE);
        add(removeLink);
    }

    @Override
    public void updateControlPanel() {
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(this);
        }
    }

    @Override
    public void notifyAccountWasRemoved(@Nonnull AccountRow accountRow) {
        info("Учетная запись {" + accountRow.getId() + "} была успешно удалена.");
    }

    @Nonnull
    private AccountToolbarPresenter presenter() {
        return this.presenter;
    }

    private final class SelectedItemModel extends AbstractReadOnlyModel<AccountRow> {

        public SelectedItemModel() {
            super();
        }

        @Override
        public AccountRow getObject() {
            return presenter().getSelectedItem();
        }

    }
}
