package promolo.wicket.account.instractructure.persistence;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.account.domain.Person;

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
        this.accountRepository.add(new Account("admin", new Person("Mr. Administrator", "Иван", "Иванович", "Иванов")));
        this.accountRepository.add(new Account("user", new Person("Сидор", "Иванович", "Берия")));
        this.accountRepository.add(new Account("jdou", new Person("Евгений", "Петрович", "Штирлиц")));
    }

}
