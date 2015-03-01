package promolo.wicket.account.ui;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class AccountRecordSelected {

    private final AccountRecord accountRecord;

    public AccountRecordSelected(@Nonnull AccountRecord accountRecord) {
        this.accountRecord = accountRecord;
    }

    @Nonnull
    public AccountRecord accountRecord() {
        return this.accountRecord;
    }

}
