package promolo.wicket.core.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HonorApplicationException {

}
