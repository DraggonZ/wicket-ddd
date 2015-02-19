package promolo.wicket.core.domain;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class Validation {

    @Nonnull
    public static Validator validator() {
        return javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static <T> void assertNotValid(@Nonnull Set<ConstraintViolation<T>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            throw new IllegalStateException("ошибка валидации: " + constraintViolations);
        }
    }

    private Validation() {
        // nop
    }
}
