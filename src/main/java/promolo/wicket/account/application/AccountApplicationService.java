package promolo.wicket.account.application;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.core.application.stereotype.ApplicationComponent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationComponent
public class AccountApplicationService {

    @Inject
    private AccountRepository accountRepository;

    @CheckForNull
    public Account findAccountById(@Nonnull String id) {
        return this.accountRepository.findById(id);
    }

}
