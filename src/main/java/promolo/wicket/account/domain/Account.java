package promolo.wicket.account.domain;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import promolo.wicket.core.domain.DomainEvent;
import promolo.wicket.core.domain.DomainEventPublisher;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class Account {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+")
    @Size(min = 3, max = 20)
    private String id;

    @NotNull
    @Size(min = 3, max = 64)
    private String title;

    public Account(@Nonnull String id, @Nonnull String title) {
        this.id = id;
        this.title = title;
        assertNotValid(validator().validate(this));
    }

    @Nonnull
    public String id() {
        return this.id;
    }

    @Nonnull
    public String title() {
        return this.title;
    }

    public void changeTitle(@Nonnull String title) {
        assertNotValid(validator().validateValue(Account.class, "title", title));
        if (!StringUtils.equals(this.title, title)) {
            this.title = title;
            publish(new AccountTitleChanged(id(), title()));
        }
    }

    protected final void publish(@Nonnull DomainEvent domainEvent) {
        DomainEventPublisher.instance().publish(domainEvent);
    }

    protected static <T> void assertNotValid(@Nonnull Set<ConstraintViolation<T>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            throw new IllegalStateException("ошибка валидации: " + constraintViolations);
        }
    }

    @Nonnull
    protected static Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
