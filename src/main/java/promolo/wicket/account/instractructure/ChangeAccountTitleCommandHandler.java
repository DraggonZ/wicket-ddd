package promolo.wicket.account.instractructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

import promolo.wicket.account.application.ChangeAccountTitleCommand;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
public class ChangeAccountTitleCommandHandler {

    public void handle(@Observes(during = TransactionPhase.IN_PROGRESS) ChangeAccountTitleCommand command) {
        command.validate();
    }

}
