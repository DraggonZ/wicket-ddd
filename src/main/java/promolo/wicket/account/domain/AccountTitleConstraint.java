package promolo.wicket.account.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@NotNull(message = "наименование учетной записи не указано")
@Size(min = 3, max = 64, message = "допустимая минимальная длина наименования учетной записи {min}, максимальная {max}")
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
public @interface AccountTitleConstraint {

    public String message() default "";

    public Class<?>[] groups() default { };

    public Class<? extends Payload>[] payload() default { };

}
