package promolo.wicket.core.infrastructure;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional
public class CdiApplicationCommandExecutor implements ApplicationCommandExecutor {

    @Inject
    @Any
    private Event<ApplicationCommand> applicationCommandEvent;

    @Override
    public void execute(@Nonnull ApplicationCommand command) {
        command.validate();
        this.applicationCommandEvent.fire(command);
    }

}
