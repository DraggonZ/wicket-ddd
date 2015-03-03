package promolo.wicket.account.domain;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class AccountRemoved implements DomainEvent {

    private final String id;

    public AccountRemoved(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

}
