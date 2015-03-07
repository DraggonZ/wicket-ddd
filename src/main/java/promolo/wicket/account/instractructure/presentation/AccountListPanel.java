package promolo.wicket.account.instractructure.presentation;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

import promolo.wicket.account.domain.AccountCreated;
import promolo.wicket.account.domain.AccountPersonChanged;
import promolo.wicket.account.domain.AccountRemoved;
import promolo.wicket.account.ui.list.AccountListPresenter;
import promolo.wicket.account.ui.list.AccountListView;
import promolo.wicket.account.ui.list.AccountRow;
import promolo.wicket.account.ui.list.SelectAccount;
import promolo.wicket.core.ui.component.AjaxRefreshOnDomainEvent;
import promolo.wicket.core.ui.component.ViewEventListener;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListPanel extends Panel implements AccountListView {

    private static final AjaxChannel ACCOUNT_SELECTION_AJAX_CHANNEL = new AjaxChannel("AccountSelectionGroup", AjaxChannel.Type.DROP);

    private final AccountListPresenter presenter = new AccountListPresenter(this);

    public AccountListPanel(String id) {
        super(id);
        add(new ViewEventListener(presenter()));

        WebMarkupContainer tableWrapper = new WebMarkupContainer("tableWrapper");
        tableWrapper.add(AjaxRefreshOnDomainEvent.of(AccountCreated.class));
        tableWrapper.add(AjaxRefreshOnDomainEvent.of(AccountPersonChanged.class));
        tableWrapper.add(AjaxRefreshOnDomainEvent.of(AccountRemoved.class));

        RadioGroup<AccountRow> radioGroup = new RadioGroup<>("selectionGroup", new SelectedAccountRecordModel());
        radioGroup.add(new AjaxFormChoiceComponentUpdatingBehavior() {

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.setChannel(ACCOUNT_SELECTION_AJAX_CHANNEL);
            }

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                AccountRow accountRow = (AccountRow) getComponent().getDefaultModelObject();
                send(getPage(), Broadcast.BREADTH, new SelectAccount(accountRow));
            }

        });
        tableWrapper.add(radioGroup);

        ListView<AccountRow> listView = new ListView<AccountRow>("row", new AccountRecordListModel()) {

            @Override
            protected void populateItem(final ListItem<AccountRow> item) {
                AccountRow accountRow = item.getModelObject();
                item.add(new Radio<>("selection", item.getModel()));
                item.add(new Label("id", accountRow.getId()));
                item.add(new Label("title", accountRow.getTitle()));
                item.add(new Label("realName", accountRow.getRealName()).add(new HideRealName(accountRow)));
            }

        };
        radioGroup.add(listView);

        add(tableWrapper);
    }

    @Override
    public void updateAccountList() {
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(get("tableWrapper"));
        }
    }

    @Nonnull
    private AccountListPresenter presenter() {
        return this.presenter;
    }

    private final class SelectedAccountRecordModel extends LoadableDetachableModel<AccountRow> {

        public SelectedAccountRecordModel() {
            super();
        }

        @Override
        protected AccountRow load() {
            return presenter().getSelectedRowItem();
        }

    }

    private final class AccountRecordListModel extends LoadableDetachableModel<List<AccountRow>> {

        public AccountRecordListModel() {
            super();
        }

        @Override
        protected List<AccountRow> load() {
            return presenter().getAccountListModel();
        }

    }

    private final static class HideRealName extends Behavior {

        private final AccountRow accountRow;

        public HideRealName(AccountRow accountRow) {
            this.accountRow = accountRow;
        }

        @Override
        public void onConfigure(Component component) {
            super.onConfigure(component);
            component.setVisible(this.accountRow.isRealNameDifferFromTitle());
        }

    }

}
