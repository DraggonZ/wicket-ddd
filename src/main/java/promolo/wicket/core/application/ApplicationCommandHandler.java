package promolo.wicket.core.application;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author lexx
 */
public interface ApplicationCommandHandler<T extends ApplicationCommand> {

    public void handle(@Nonnull T command);

}
