package promolo.wicket.account.domain;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import promolo.wicket.core.domain.ConcurrencySafe;
import promolo.wicket.core.domain.ConcurrencyViolationException;
import promolo.wicket.core.domain.DomainObject;
import promolo.wicket.core.domain.Validation;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class Account extends DomainObject implements ConcurrencySafe {

    private long version;

    @AccountIdConstraint
    private String id;

    @NotNull(message = "не указаны персональные данные учетной записи")
    private Person person;

    public Account(@Nonnull String id, @Nonnull Person person) {
        this.id = id;
        this.person = person;
        Validation.assertNotValid(Validation.validator().validate(this));
        publish(new AccountCreated(id, person.title(), person.firstName(), person.middleName(), person.lastName()));
    }

    @Nonnull
    public String id() {
        return this.id;
    }

    @Nonnull
    public Person person() {
        return this.person;
    }

    public void changePerson(@Nonnull Person person) {
        Validation.assertNotValid(Validation.validator().validateValue(Account.class, "person", person));
        if (!Objects.equals(this.person, person)) {
            this.person = person;
            publish(new AccountPersonChanged(id(), person.title(), person.firstName(), person.middleName(), person.lastName()));
        }
    }

    @Override
    public long concurrencyVersion() {
        return this.version;
    }

    @Override
    public void failWhenConcurrencyViolation(long version) {
        if (version != concurrencyVersion()) {
            throw new ConcurrencyViolationException();
        }
    }

    protected Account() {
        // nop
    }
}
