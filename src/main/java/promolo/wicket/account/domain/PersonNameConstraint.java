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
@NotNull(message = "имя пользователя не указано")
@Size(min = 1, max = 20, message = "допустимая минимальная длина имени пользователя {min}, максимальная {max}")
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
public @interface PersonNameConstraint {

    public String message() default "";

    public Class<?>[] groups() default { };

    public Class<? extends Payload>[] payload() default { };

}
