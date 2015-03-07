package promolo.wicket.account.ui.toolbar;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class RemoveAccount {

    private final String id;

    public RemoveAccount(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

}
