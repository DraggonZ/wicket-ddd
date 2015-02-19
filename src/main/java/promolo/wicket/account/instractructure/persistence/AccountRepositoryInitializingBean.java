package promolo.wicket.account.instractructure.persistence;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Startup
@Singleton
public class AccountRepositoryInitializingBean {

    @Inject
    private AccountRepository accountRepository;

    @PostConstruct
    public void initializeRepository() {
        this.accountRepository.add(new Account("admin", "Administrator"));
        this.accountRepository.add(new Account("user", "Just user"));
        this.accountRepository.add(new Account("jdou", "Guest Star"));
    }

}
