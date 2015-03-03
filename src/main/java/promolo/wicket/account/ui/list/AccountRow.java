package promolo.wicket.account.ui.list;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import promolo.wicket.account.domain.Account;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountRow implements Serializable {

    private String id;

    private String title;

    @Nonnull
    public String getId() {
        return this.id;
    }

    @Nonnull
    public String getTitle() {
        return this.title;
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
        return new EqualsBuilder().append(this.id, rhs.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    /* package */ AccountRow(@Nonnull Account account) {
        this.id = account.id();
        this.title = account.person().title();
    }

}
