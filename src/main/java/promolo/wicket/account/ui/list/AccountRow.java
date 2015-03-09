package promolo.wicket.account.ui.list;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public boolean isRealNameDifferFromTitle() {
        return !Objects.equals(getTitle(), getRealName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccountRow rhs = (AccountRow) obj;
        return new EqualsBuilder().append(this.id, rhs.id).append(this.title, rhs.title).append(this.realName, rhs.realName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.title).append(this.realName).toHashCode();
    }

}
