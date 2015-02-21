package promolo.wicket.account.application;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
@Transactional
public class AccountApplicationService {

    @Inject
    private AccountRepository accountRepository;

    @CheckForNull
    public Account findAccountById(@Nonnull String id) {
        return this.accountRepository.findById(id);
    }

}
