package promolo.wicket.core.infrastructure.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import promolo.wicket.core.domain.DomainEventPublisherCleaner;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class DomainEventPublisherCleanerFilter implements Filter {

    @Inject
    private DomainEventPublisherCleaner domainEventPublisherCleaner;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nop
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            this.domainEventPublisherCleaner.cleanup();
        }
    }

    @Override
    public void destroy() {
        // nop
    }

}
