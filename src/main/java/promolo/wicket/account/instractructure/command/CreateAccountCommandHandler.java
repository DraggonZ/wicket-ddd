package promolo.wicket.account.instractructure.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.account.application.CreateAccountCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.account.domain.Person;
import promolo.wicket.core.application.ApplicationCommandHandler;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class CreateAccountCommandHandler {

    @Inject
    private AccountRepository accountRepository;

    public void handle(@Observes @ApplicationCommandHandler CreateAccountCommand command) {
        Person person = new Person(command.getTitle(), command.getFirstName(), command.getMiddleName(), command.getLastName());
        Account account = new Account(command.getId(), person);
        this.accountRepository.add(account);
    }

}
