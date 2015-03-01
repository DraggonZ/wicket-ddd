package promolo.wicket.account.domain;

import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountRepository {

    public void add(@Nonnull Account account);

    @CheckForNull
    public Account findById(@Nonnull String id);

    @Nonnull
    public List<Account> listAll();

}
