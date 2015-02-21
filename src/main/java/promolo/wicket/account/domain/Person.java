package promolo.wicket.account.domain;

import static org.apache.commons.lang3.StringUtils.*;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import promolo.wicket.core.domain.DomainObject;
import promolo.wicket.core.domain.Validation;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class Person extends DomainObject {

    @NotNull(message = "наименование пользователя не указано")
    @PersonTitleConstraint
    private String title;

    @PersonNameConstraint
    private String firstName;

    @PersonNameConstraint
    private String middleName;

    @PersonNameConstraint
    private String lastName;

    public Person(@Nonnull String firstName, @Nonnull String middleName, @Nonnull String lastName) {
        this(PersonTitle.build(firstName, middleName, lastName), firstName, middleName, lastName);
    }

    public Person(@Nonnull String title, @Nonnull String firstName, @Nonnull String middleName, @Nonnull String lastName) {
        this.title = title;
        this.firstName = capitalize(firstName);
        this.middleName = capitalize(middleName);
        this.lastName = capitalize(lastName);
        Validation.assertNotValid(Validation.validator().validate(this));
    }

    @Nonnull
    public String title() {
        return this.title;
    }

    @Nonnull
    public String firstName() {
        return this.firstName;
    }

    @Nonnull
    public String middleName() {
        return this.middleName;
    }

    @Nonnull
    public String lastName() {
        return this.lastName;
    }

    @Nonnull
    public Person withTitle(@Nonnull @PersonTitleConstraint String title) {
        return new Person(title, firstName(), middleName(), lastName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Person rhs = (Person) obj;
        return new EqualsBuilder().append(this.title, rhs.title).append(this.firstName, rhs.firstName)
                .append(this.middleName, rhs.middleName).append(this.lastName, rhs.lastName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.title).append(this.firstName).append(this.middleName).append(this.lastName).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("title", this.title).append("firstName", this.firstName)
                .append("middleName", this.middleName).append("lastName", this.lastName).toString();
    }

    protected Person() {
        // nop
    }
}
