package promolo.wicket.account.instractructure.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.account.application.RemoveAccountCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.core.application.ApplicationCommandHandler;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class RemoveAccountCommandHandler {

    @Inject
    private AccountRepository accountRepository;

    public void handle(@Observes @ApplicationCommandHandler RemoveAccountCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account != null) {
            this.accountRepository.remove(account);
            // TODO publish Domain Event
        }
    }

}
