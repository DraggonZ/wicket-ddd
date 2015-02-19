package promolo.wicket.account.domain;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

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

    private Long version;

    @AccountIdConstraint
    private String id;

    @AccountTitleConstraint
    private String title;

    public Account(@Nonnull String id, @Nonnull String title) {
        this.id = id;
        this.title = title;
        Validation.assertNotValid(Validation.validator().validate(this));
    }

    @Nonnull
    public String id() {
        return this.id;
    }

    @Nonnull
    public String title() {
        return this.title;
    }

    public void changeTitle(@Nonnull @AccountTitleConstraint String title) {
        Validation.assertNotValid(Validation.validator().validateValue(Account.class, "title", title));
        if (!StringUtils.equals(this.title, title)) {
            this.title = title;
            publish(new AccountTitleChanged(id(), title()));
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
