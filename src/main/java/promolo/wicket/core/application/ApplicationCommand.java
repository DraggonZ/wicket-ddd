package promolo.wicket.core.application;

import static promolo.wicket.core.domain.Validation.*;

import java.io.Serializable;

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

}
