package promolo.wicket.core.infrastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandExecutor;
import promolo.wicket.core.application.ApplicationCommandHandlerRegistry;
import promolo.wicket.core.application.Handles;
import promolo.wicket.core.application.stereotype.ApplicationComponent;
import promolo.wicket.core.domain.DomainEvent;
import promolo.wicket.core.domain.DomainEventPublisher;
import promolo.wicket.core.domain.DomainEventSubscriber;
import promolo.wicket.core.ui.notification.DomainEventNotificationListener;

import com.google.common.collect.Lists;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationComponent
public class CdiApplicationCommandExecutor implements ApplicationCommandExecutor {

    @Inject
    @Handles
    private Event<ApplicationCommand> applicationCommandEvent;

    @Inject
    private Event<ProcessingResult> processingResultEvent;

    @Inject
    private ApplicationCommandHandlerRegistry applicationCommandHandlerRegistry;

    @Inject
    private Instance<DomainEventNotificationListener> domainEventNotificationListeners;

    @Override
    public void execute(@Nonnull ApplicationCommand command) {
        command.validate();
        if (!this.applicationCommandHandlerRegistry.isApplicationCommandHandlerExists(command)) {
            throw new IllegalStateException("не найден обработчик для команды " + command);
        }
        DomainEventCollector domainEventCollector = new DomainEventCollector();
        DomainEventPublisher.instance().subscribe(domainEventCollector);
        this.applicationCommandEvent.fire(command);
        this.processingResultEvent.fire(new ProcessingResult(command, domainEventCollector.getDomainEvents()));
    }

    public void notifyDomainEventNotificationListeners(
            @Observes(during = TransactionPhase.AFTER_SUCCESS) ProcessingResult processingResult) {
        if (!processingResult.getDomainEvents().isEmpty()) {
            Iterator<DomainEventNotificationListener> iterator = this.domainEventNotificationListeners.iterator();
            if (iterator.hasNext()) {
                doNotifyDomainEventNotificationListeners(processingResult, iterator);
            }
        }
    }

    private static void doNotifyDomainEventNotificationListeners(@Nonnull ProcessingResult processingResult,
            @Nonnull Iterator<DomainEventNotificationListener> iterator) {
        List<DomainEventNotificationListener> listeners = Lists.newArrayList(iterator);
        for (DomainEvent domainEvent : processingResult.getDomainEvents()) {
            for (DomainEventNotificationListener listener : listeners) {
                listener.notify(domainEvent);
            }
        }
    }

    private static final class DomainEventCollector implements DomainEventSubscriber<DomainEvent> {

        private List<DomainEvent> domainEvents;

        @Nonnull
        public List<DomainEvent> getDomainEvents() {
            return (this.domainEvents == null ? Collections.<DomainEvent>emptyList() : this.domainEvents);
        }

        @Override
        public void handleEvent(@Nonnull DomainEvent domainEvent) {
            if (this.domainEvents == null) {
                this.domainEvents = Lists.newArrayListWithExpectedSize(3);
            }
            this.domainEvents.add(domainEvent);
        }

        @Nonnull
        @Override
        public Class<DomainEvent> subscribedToEventType() {
            return DomainEvent.class;
        }

    }

    private static final class ProcessingResult {

        private final ApplicationCommand command;

        private final List<DomainEvent> domainEvents;

        public ProcessingResult(@Nonnull ApplicationCommand command, @Nonnull List<DomainEvent> domainEvents) {
            this.command = command;
            this.domainEvents = new ArrayList<>(domainEvents);
        }

        @Nonnull
        public ApplicationCommand getCommand() {
            return this.command;
        }

        @Nonnull
        public List<DomainEvent> getDomainEvents() {
            return this.domainEvents;
        }

    }

}
