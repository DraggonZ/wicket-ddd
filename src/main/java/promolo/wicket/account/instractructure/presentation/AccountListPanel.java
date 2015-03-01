package promolo.wicket.account.instractructure.presentation;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.ui.AccountRecord;
import promolo.wicket.account.ui.AccountRecordModel;
import promolo.wicket.account.ui.AccountRecordSelected;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountListPanel extends Panel {

    private static final AjaxChannel ACCOUNT_SELECTION_AJAX_CHANNEL = new AjaxChannel("AccountSelectionGroup", AjaxChannel.Type.DROP);

    @Inject
    private AccountRecordModel accountRecordModel;

    public AccountListPanel(String id) {
        super(id);

        setVersioned(false);

        WebMarkupContainer tableWrapper = new WebMarkupContainer("tableWrapper");
        tableWrapper.setOutputMarkupId(true);

        RadioGroup<AccountRecord> radioGroup = new RadioGroup<>("selectionGroup", new Model<AccountRecord>());
        radioGroup.add(new AjaxFormChoiceComponentUpdatingBehavior() {

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.setChannel(ACCOUNT_SELECTION_AJAX_CHANNEL);
            }

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                AccountRecord accountRecord = (AccountRecord) getComponent().getDefaultModelObject();
                send(getPage(), Broadcast.BREADTH, new AccountRecordSelected(accountRecord));
            }

        });
        tableWrapper.add(radioGroup);

        ListView<AccountRecord> listView = new ListView<AccountRecord>("row", new AccountRecordListModel()) {

            @Override
            protected void populateItem(ListItem<AccountRecord> item) {
                item.add(new Radio<>("selection", item.getModel()));
                item.add(new Label("id", item.getModelObject().getId()));
                item.add(new Label("title", item.getModelObject().getTitle()));
            }

        };
        listView.setReuseItems(true);
        radioGroup.add(listView);

        add(tableWrapper);
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
