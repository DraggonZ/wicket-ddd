package promolo.wicket.account.instractructure.wicket;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import promolo.wicket.account.application.ChangeAccountTitleCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountEditorPanel extends Panel {

    @Inject
    private ApplicationCommandExecutor applicationCommandExecutor;

    public AccountEditorPanel(String id, IModel<?> model) {
        super(id, model);
        add(new Link<Void>("save") {

            @Override
            public void onClick() {
                AccountEditorPanel.this.applicationCommandExecutor.execute(new ChangeAccountTitleCommand("jdou", "John Dou"));
                success("Операция выполнена успешна.");
            }

        });
    }

}
