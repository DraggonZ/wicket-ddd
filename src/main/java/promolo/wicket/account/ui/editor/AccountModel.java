package promolo.wicket.account.ui.editor;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.bindgen.Bindable;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountIdConstraint;
import promolo.wicket.account.domain.PersonNameConstraint;
import promolo.wicket.account.domain.PersonTitleConstraint;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Bindable
public class AccountModel implements Serializable {

    @AccountIdConstraint
    private String id;

    private Long version;

    @PersonTitleConstraint
    private String title;

    @PersonNameConstraint
    private String firstName;

    @PersonNameConstraint
    private String middleName;

    @PersonNameConstraint
    private String lastName;

    public AccountModel() {
        // nop
    }

    public AccountModel(@Nonnull Account account) {
        setId(account.id());
        setVersion(account.concurrencyVersion());
        setTitle(account.person().title());
        setFirstName(account.person().firstName());
        setMiddleName(account.person().middleName());
        setLastName(account.person().lastName());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
