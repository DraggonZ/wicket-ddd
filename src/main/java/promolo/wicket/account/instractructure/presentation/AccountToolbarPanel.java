package promolo.wicket.account.instractructure.presentation;

import javax.annotation.Nonnull;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;

import promolo.wicket.account.domain.AccountRemoved;
import promolo.wicket.account.ui.toolbar.AccountToolbarPresenter;
import promolo.wicket.account.ui.toolbar.AccountToolbarView;
import promolo.wicket.account.ui.toolbar.AddAccount;
import promolo.wicket.account.ui.toolbar.RemoveAccount;
import promolo.wicket.core.ui.component.DisableEmptyComponent;
import promolo.wicket.core.ui.component.ViewEventListener;

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
        add(new ViewEventListener(presenter()));
        AjaxLink<Void> addLink = new AjaxLink<Void>("add") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new AddAccount());
            }

        };
        add(addLink);

        AjaxLink<String> removeLink = new AjaxLink<String>("remove", new SelectedAccountIdModel()) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new RemoveAccount(getModelObject()));
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
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if (event.getPayload() instanceof AccountRemoved) {
            AccountRemoved accountRemoved = (AccountRemoved) event.getPayload();
            info("Учетная запись {" + accountRemoved.id() + "} была успешно удалена.");
            updateControlPanel();
        }
    }

    @Nonnull
    private AccountToolbarPresenter presenter() {
        return this.presenter;
    }

    private final class SelectedAccountIdModel extends AbstractReadOnlyModel<String> {

        public SelectedAccountIdModel() {
            super();
        }

        @Override
        public String getObject() {
            return presenter().getSelectedAccountId();
        }

    }
}
