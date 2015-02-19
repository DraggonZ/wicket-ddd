package promolo.wicket.account.domain;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountTitleChanged implements DomainEvent {

    private String id;

    private String title;

    public AccountTitleChanged(@Nonnull String id, @Nonnull String title) {
        this.id = id;
        this.title = title;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

    @Nonnull
    public String title() {
        return this.title;
    }

}
