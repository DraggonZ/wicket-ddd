package promolo.wicket.account.ui;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class AccountRemoveRequested {

    private final String id;

    public AccountRemoveRequested(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

}
