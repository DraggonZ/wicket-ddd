package promolo.wicket.account.instractructure.command;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import promolo.wicket.account.application.CreateAccountCommand;
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
@Handles(CreateAccountCommand.class)
public class CreateAccountCommandHandler implements ApplicationCommandHandler<CreateAccountCommand> {

    @Inject
    private AccountRepository accountRepository;

    @Override
    public void handle(@Nonnull CreateAccountCommand command) {
        Person person = new Person(command.getTitle(), command.getFirstName(), command.getMiddleName(), command.getLastName());
        Account account = new Account(command.getId(), person);
        this.accountRepository.add(account);
    }

}
