package promolo.wicket.account.application;

import javax.annotation.Nonnull;

import promolo.wicket.account.domain.AccountIdConstraint;
import promolo.wicket.core.application.ApplicationCommand;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class RemoveAccountCommand extends ApplicationCommand {

    @AccountIdConstraint
    private String id;

    public RemoveAccountCommand(@Nonnull String id) {
        super();
        setId(id);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
