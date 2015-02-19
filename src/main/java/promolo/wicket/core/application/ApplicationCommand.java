package promolo.wicket.core.application;

import static promolo.wicket.core.domain.Validation.*;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class ApplicationCommand implements Serializable {

    protected ApplicationCommand() {
        // nop
    }

    public void validate() {
        assertNotValid(validator().validate(this));
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
