package promolo.wicket.account.instractructure.command;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import promolo.wicket.account.application.RemoveAccountCommand;
import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRemoved;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.core.application.ApplicationCommandHandler;
import promolo.wicket.core.application.Handles;
import promolo.wicket.core.domain.DomainEventPublisher;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Handles(RemoveAccountCommand.class)
public class RemoveAccountCommandHandler implements ApplicationCommandHandler<RemoveAccountCommand> {

    @Inject
    private AccountRepository accountRepository;

    @Override
    public void handle(@Nonnull RemoveAccountCommand command) {
        Account account = this.accountRepository.findById(command.getId());
        if (account != null) {
            this.accountRepository.remove(account);
            DomainEventPublisher.instance().publish(new AccountRemoved(account.id()));
        }
    }

}
