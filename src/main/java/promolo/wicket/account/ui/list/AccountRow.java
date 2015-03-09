package promolo.wicket.account.ui.list;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Nonnull;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.PersonTitle;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountRow implements Serializable {

    private String id;

    private String title;

    private String realName;

    public AccountRow(@Nonnull Account account) {
        this.id = account.id();
        this.title = account.person().title();
        this.realName = PersonTitle.build(account.person());
    }

    @Nonnull
    public String getId() {
        return this.id;
    }

    @Nonnull
    public String getTitle() {
        return this.title;
    }

    @Nonnull
    public String getRealName() {
        return this.realName;
    }

    public boolean needDisplayRealName() {
        return !Objects.equals(getTitle(), getRealName());
    }

}
