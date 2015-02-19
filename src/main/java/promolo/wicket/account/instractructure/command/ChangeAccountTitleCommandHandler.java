package promolo.wicket.account.instractructure.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.account.application.ChangeAccountTitleCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class ChangeAccountTitleCommandHandler {

    @Inject
    private AccountRepository accountRepository;

    public void handle(@Observes(during = TransactionPhase.IN_PROGRESS) ChangeAccountTitleCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account == null) {
            throw new IllegalStateException("не найдена учетная запись " + command.getId());
        }
        account.changeTitle(command.getTitle());
    }

}
