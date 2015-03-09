package promolo.wicket.account.ui.list;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class SelectAccount {

    private final String id;

    public SelectAccount(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

}
