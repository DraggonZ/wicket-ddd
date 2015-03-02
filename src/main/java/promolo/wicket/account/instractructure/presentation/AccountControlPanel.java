package promolo.wicket.account.instractructure.presentation;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.application.RemoveAccountCommand;
import promolo.wicket.account.ui.AccountRemoveRequested;
import promolo.wicket.account.ui.NewAccountRequested;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.ui.component.DisableEmptyComponent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountControlPanel extends Panel {

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountControlPanel(String id) {
        super(id);
        setOutputMarkupId(true);

        AjaxLink<Void> addLink = new AjaxLink<Void>("add") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new NewAccountRequested());
            }

        };
        add(addLink);

        AjaxLink<String> removeLink = new AjaxLink<String>("remove", new Model<String>()) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                AccountControlPanel.this.applicationCommandExecutor.execute(new RemoveAccountCommand(getModelObject()));
                send(getPage(), Broadcast.BREADTH, new AccountRemoveRequested(getModelObject()));
            }

        };
        removeLink.add(DisableEmptyComponent.INSTANCE);
        add(removeLink);
    }

    public void track(String id) {
        get("remove").setDefaultModelObject(id);
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(this);
        }
    }

}
