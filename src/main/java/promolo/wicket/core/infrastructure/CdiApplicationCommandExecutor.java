package promolo.wicket.core.infrastructure;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.application.ApplicationCommandHandler;
import promolo.wicket.core.application.ApplicationCommandHandlerRegistry;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional
public class CdiApplicationCommandExecutor implements ApplicationCommandExecutor {

    @Inject
    @ApplicationCommandHandler
    private Event<ApplicationCommand> applicationCommandEvent;

    @Inject
    private ApplicationCommandHandlerRegistry applicationCommandHandlerRegistry;

    @Override
    public void execute(@Nonnull ApplicationCommand command) {
        command.validate();
        if (!this.applicationCommandHandlerRegistry.isApplicationCommandHandlerExists(command)) {
            throw new IllegalStateException("не найден обработчик для команды " + command);
        }
        this.applicationCommandEvent.fire(command);
    }

}
