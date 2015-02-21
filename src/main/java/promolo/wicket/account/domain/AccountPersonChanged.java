package promolo.wicket.account.domain;

import javax.annotation.Nonnull;

import promolo.wicket.core.domain.DomainEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class AccountPersonChanged implements DomainEvent {

    private String id;

    private String title;

    private String firstName;

    private String middleName;

    private String lastName;

    public AccountPersonChanged(@Nonnull String id, @Nonnull String title, @Nonnull String firstName, @Nonnull String middleName,
            @Nonnull String lastName) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    @Nonnull
    public String id() {
        return this.id;
    }

    @Nonnull
    public String title() {
        return this.title;
    }

    @Nonnull
    public String firstName() {
        return this.firstName;
    }

    @Nonnull
    public String middleName() {
        return this.middleName;
    }

    @Nonnull
    public String lastName() {
        return this.lastName;
    }

}
