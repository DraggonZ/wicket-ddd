package promolo.wicket.account.ui.list;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class SelectAccount {

    private final AccountRow accountRow;

    public SelectAccount(@Nonnull AccountRow accountRow) {
        this.accountRow = accountRow;
    }

    @Nonnull
    public AccountRow accountRowItem() {
        return this.accountRow;
    }

}
