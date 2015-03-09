package promolo.wicket.account.instractructure.command;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import promolo.wicket.account.application.ChangeAccountPersonCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.account.domain.Person;
import promolo.wicket.core.application.ApplicationCommandHandler;
import promolo.wicket.core.application.Handles;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Handles(ChangeAccountPersonCommand.class)
public class ChangeAccountPersonCommandHandler implements ApplicationCommandHandler<ChangeAccountPersonCommand> {

    @Inject
    private AccountRepository accountRepository;

    @Override
    public void handle(@Nonnull ChangeAccountPersonCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account == null) {
            throw new IllegalStateException("не найдена учетная запись " + command.getId());
        }
        account.failWhenConcurrencyViolation(command.getVersion());
        account.changePerson(new Person(command.getTitle(), command.getFirstName(), command.getMiddleName(), command.getLastName()));
    }

}
