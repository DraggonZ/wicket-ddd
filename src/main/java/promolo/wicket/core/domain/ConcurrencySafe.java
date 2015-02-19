package promolo.wicket.core.domain;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface ConcurrencySafe {

    public long concurrencyVersion();

    /**
     * @throws promolo.wicket.core.domain.ConcurrencyViolationException
     */
    public void failWhenConcurrencyViolation(long version);

}
