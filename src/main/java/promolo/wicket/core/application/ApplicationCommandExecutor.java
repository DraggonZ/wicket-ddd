package promolo.wicket.core.application;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface ApplicationCommandExecutor {

    public void execute(@Nonnull ApplicationCommand command);

}
