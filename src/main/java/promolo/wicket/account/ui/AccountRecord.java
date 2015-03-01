package promolo.wicket.account.ui;

import java.io.Serializable;

import javax.annotation.Nonnull;

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

    /* package */ AccountRecord(@Nonnull Account account) {
        this.id = account.id();
        this.title = account.person().title();
    }

}

