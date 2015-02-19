package promolo.wicket.account.application;

import promolo.wicket.account.domain.AccountIdConstraint;
import promolo.wicket.account.domain.AccountTitleConstraint;
import promolo.wicket.core.application.ApplicationCommand;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class ChangeAccountTitleCommand extends ApplicationCommand {

    @AccountIdConstraint
    private String id;

    @AccountTitleConstraint
    private String title;

    public ChangeAccountTitleCommand(String id) {
        this(id, null);
    }

    public ChangeAccountTitleCommand(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
