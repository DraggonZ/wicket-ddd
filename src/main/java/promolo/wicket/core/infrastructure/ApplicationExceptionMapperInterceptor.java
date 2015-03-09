package promolo.wicket.core.infrastructure;

import javax.annotation.Nonnull;
import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.RollbackException;

import promolo.wicket.core.application.stereotype.HonorApplicationException;

/**
 * TODO javadoc
 *
 * @author Александр
 */
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 100)
@HonorApplicationException
public class ApplicationExceptionMapperInterceptor {

    @AroundInvoke
    public Object invoke(@Nonnull InvocationContext invocationContext) throws Exception {
        try {
            return invocationContext.proceed();
        } catch (Exception ex) {
            if (ex instanceof RollbackException && ex.getCause() instanceof Exception) {
                throw (Exception) ex.getCause();
            } else {
                throw ex;
            }
        }
    }

}
