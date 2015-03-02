package promolo.wicket.account.domain;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class AccountCreated implements DomainEvent {

    private final String id;

    private final String title;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    public AccountCreated(@Nonnull String id, @Nonnull String title, @Nonnull String firstName, @Nonnull String middleName,
            @Nonnull String lastName) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    @Nonnull
    public String getId() {
        return this.id;
    }

    @Nonnull
    public String getTitle() {
        return this.title;
    }

    @Nonnull
    public String getFirstName() {
        return this.firstName;
    }

    @Nonnull
    public String getMiddleName() {
        return this.middleName;
    }

    @Nonnull
    public String getLastName() {
        return this.lastName;
    }

}
