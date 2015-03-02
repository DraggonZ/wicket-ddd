package promolo.wicket.account.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

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

    @CheckForNull
    public AccountRecord findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            Account account = this.accountRepository.findById(id);
            if (account != null) {
                return new AccountRecord(account);
            }
        }
        return null;
    }

}
