package promolo.wicket.account.application;

import org.bindgen.Bindable;

import promolo.wicket.account.domain.AccountIdConstraint;
import promolo.wicket.account.domain.PersonNameConstraint;
import promolo.wicket.account.domain.PersonTitleConstraint;
import promolo.wicket.core.application.ApplicationCommand;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Bindable
public class CreateAccountCommand extends ApplicationCommand {

    @AccountIdConstraint
    private String id;

    @PersonTitleConstraint
    private String title;

    @PersonNameConstraint
    private String firstName;

    @PersonNameConstraint
    private String middleName;

    @PersonNameConstraint
    private String lastName;

    public CreateAccountCommand(String id) {
        this(id, null, null, null, null);
    }

    public CreateAccountCommand(String id, String title, String firstName, String middleName, String lastName) {
        this.id = id;
        setTitle(title);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
