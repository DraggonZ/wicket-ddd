package promolo.wicket.core.application;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface ApplicationCommandHandlerRegistry {

    public boolean isApplicationCommandHandlerExists(@Nonnull ApplicationCommand command);

}
