package promolo.wicket.account.instractructure.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import promolo.wicket.account.application.ChangeAccountPersonCommand;
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
public class ChangeAccountPersonCommandHandler {

    @Inject
    private AccountRepository accountRepository;

    public void handle(@Observes @ApplicationCommandHandler ChangeAccountPersonCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account == null) {
            throw new IllegalStateException("не найдена учетная запись " + command.getId());
        }
        account.failWhenConcurrencyViolation(command.getVersion());
        if (StringUtils.isEmpty(command.getTitle())) {
            account.changePerson(new Person(command.getFirstName(), command.getMiddleName(), command.getLastName()));
        } else {
            account.changePerson(new Person(command.getTitle(), command.getFirstName(), command.getMiddleName(), command.getLastName()));
        }
    }

}
