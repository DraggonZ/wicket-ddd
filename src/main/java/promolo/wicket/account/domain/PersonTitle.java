package promolo.wicket.account.domain;

import static org.apache.commons.lang3.StringUtils.*;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class PersonTitle {

    @Nonnull
    public static String build(@Nonnull Person person) {
        return build(person.firstName(), person.middleName(), person.lastName());
    }

    @Nonnull
    public static String build(@Nonnull String firstName, @Nonnull String middleName, @Nonnull String lastName) {
        return Joiner.on(StringUtils.SPACE).join(capitalize(lastName), capitalize(firstName), capitalize(middleName));
    }

    private PersonTitle() {
        // nop
    }
}
