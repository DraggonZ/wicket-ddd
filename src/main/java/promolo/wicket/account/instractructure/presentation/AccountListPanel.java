package promolo.wicket.account.instractructure.presentation;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

import promolo.wicket.account.ui.AccountRecord;
import promolo.wicket.account.ui.AccountRecordModel;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListPanel extends Panel {

    @Inject
    private AccountRecordModel accountRecordModel;

    public AccountListPanel(String id) {
        super(id);

        setVersioned(false);

        WebMarkupContainer table = new WebMarkupContainer("table");
        table.setOutputMarkupId(true);

        ListView<AccountRecord> listView = new ListView<AccountRecord>("row", new AccountRecordListModel()) {

            @Override
            protected void populateItem(ListItem<AccountRecord> item) {
                item.add(new Label("id", item.getModelObject().getId()));
                item.add(new Label("title", item.getModelObject().getTitle()));
            }

        };
        listView.setReuseItems(true);
        table.add(listView);

        add(table);
    }

    private final class AccountRecordListModel extends LoadableDetachableModel<List<AccountRecord>> {

        public AccountRecordListModel() {
            super();
        }

        @Override
        protected List<AccountRecord> load() {
            return AccountListPanel.this.accountRecordModel.listAccountRecords();
        }

    }

}
