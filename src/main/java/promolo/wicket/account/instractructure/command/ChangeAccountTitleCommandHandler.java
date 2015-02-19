package promolo.wicket.account.instractructure.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.account.application.ChangeAccountTitleCommand;
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
public class ChangeAccountTitleCommandHandler {

    @Inject
    private AccountRepository accountRepository;

    public void handle(@Observes @ApplicationCommandHandler ChangeAccountTitleCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account == null) {
            throw new IllegalStateException("не найдена учетная запись " + command.getId());
        }
        account.failWhenConcurrencyViolation(command.getVersion());
        account.changeTitle(command.getTitle());
    }

}
