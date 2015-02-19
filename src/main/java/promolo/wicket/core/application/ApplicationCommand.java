package promolo.wicket.core.application;

import static promolo.wicket.core.domain.Validation.*;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class ApplicationCommand {

    protected ApplicationCommand() {
        // nop
    }

    public void validate() {
        assertNotValid(validator().validate(this));
    }

}
