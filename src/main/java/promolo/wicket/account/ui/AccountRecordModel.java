package promolo.wicket.account.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import promolo.wicket.account.domain.Account;
import promolo.wicket.account.domain.AccountRepository;
import promolo.wicket.core.ui.stereotype.PresentationComponent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@PresentationComponent
public class AccountRecordModel {

    @Inject
    private AccountRepository accountRepository;

    @Nonnull
    public List<AccountRecord> listAccountRecords() {
        List<Account> accounts = this.accountRepository.listAll();
        List<AccountRecord> records = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            records.add(new AccountRecord(account));
        }
        return records;
    }

}
