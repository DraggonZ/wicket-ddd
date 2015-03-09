package promolo.wicket.core.infrastructure;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.util.AnnotationLiteral;

import promolo.wicket.core.application.ApplicationCommand;
import promolo.wicket.core.application.ApplicationCommandHandlerRegistry;
import promolo.wicket.core.application.Handles;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class CdiApplicationCommandHandlerRegistry implements Extension, ApplicationCommandHandlerRegistry {

    private final Set<Type> allApplicationCommandTypes = new HashSet<>();

    public void registerApplicationCommandHandlerQualifier(@Observes BeforeBeanDiscovery event) {
        event.addQualifier(Handles.class);
    }

    public void registerApplicationCommandObserver(@Observes ProcessObserverMethod<? extends ApplicationCommand, ?> event) {
        if (event.getObserverMethod().getObservedQualifiers().contains(HandlesLiteral.INSTANCE)) {
            this.allApplicationCommandTypes.add(event.getObserverMethod().getObservedType());
        }
    }

    @Override
    public boolean isApplicationCommandHandlerExists(@Nonnull ApplicationCommand command) {
        return this.allApplicationCommandTypes.contains(command.getClass());
    }

    private static final class HandlesLiteral extends AnnotationLiteral<Handles> implements Handles {

        public static final HandlesLiteral INSTANCE = new HandlesLiteral();

        private HandlesLiteral() {
            super();
        }

    }

}
