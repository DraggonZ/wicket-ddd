package promolo.wicket.core.ui.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import javax.transaction.Transactional;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Stereotype
@RequestScoped
@Transactional
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PresentationComponent {

}
