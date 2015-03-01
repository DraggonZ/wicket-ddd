package promolo.wicket.account.instractructure.persistence;

import java.util.List;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@ApplicationScoped
public class JpaAccountRepository implements AccountRepository {

    @PersistenceContext(unitName = "AccountPU")
    private EntityManager entityManager;

    @Override
    public void add(@Nonnull Account account) {
        this.entityManager.persist(account);
    }

    @Override
    public Account findById(@Nonnull String id) {
        return this.entityManager.find(Account.class, id);
    }

    @Nonnull
    @Override
    public List<Account> listAll() {
        TypedQuery<Account> query = this.entityManager.createQuery("from Account order by id asc", Account.class);
        return query.getResultList();
    }

}
