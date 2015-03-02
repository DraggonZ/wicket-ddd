package promolo.wicket.account.instractructure.presentation;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import promolo.wicket.account.ui.NewAccountRequested;
import promolo.wicket.core.ui.component.DisableEmptyComponent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountControlPanel extends Panel {

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
                // TODO
            }

        };
        removeLink.add(DisableEmptyComponent.INSTANCE);
        add(removeLink);
    }

    public void refresh(String selectedAccountId) {
        get("remove").setDefaultModelObject(selectedAccountId);
        AjaxRequestHandler ajaxRequestHandler = getRequestCycle().find(AjaxRequestHandler.class);
        if (ajaxRequestHandler != null) {
            ajaxRequestHandler.add(this);
        }
    }

}
