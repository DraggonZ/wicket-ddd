package promolo.wicket.core.infrastructure;

import javax.annotation.Nonnull;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.application.ApplicationCommandHandler;
import promolo.wicket.core.application.ApplicationCommandHandlerRegistry;
import promolo.wicket.core.application.stereotype.ApplicationComponent;
import promolo.wicket.core.domain.DomainEvent;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.DomainEventSubscriber;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationComponent
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
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {

            @Override
            public void handleEvent(@Nonnull DomainEvent domainEvent) {
                // TODO - донести событие до UI
            }

            @Nonnull
            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }

        });
        this.applicationCommandEvent.fire(command);
    }

}
