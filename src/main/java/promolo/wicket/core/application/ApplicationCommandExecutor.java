package promolo.wicket.core.application;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 * @see promolo.wicket.core.application.ApplicationCommandHandler
 */
public interface ApplicationCommandExecutor {

    public <T extends ApplicationCommand>void execute(@Nonnull T command);

}
