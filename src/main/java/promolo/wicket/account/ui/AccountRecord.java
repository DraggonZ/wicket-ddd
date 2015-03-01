package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bindgen.Bindable;

import promolo.wicket.account.domain.Account;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Bindable
public class AccountRecord implements Serializable {

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
        AccountRecord rhs = (AccountRecord) obj;
        return new EqualsBuilder().append(this.id, rhs.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    /* package */ AccountRecord(@Nonnull Account account) {
        this.id = account.id();
        this.title = account.person().title();
    }

}

