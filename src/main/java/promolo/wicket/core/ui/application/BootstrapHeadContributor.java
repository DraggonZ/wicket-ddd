package promolo.wicket.core.ui.application;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.resource.ResourceReference;

import javax.annotation.Nonnull;

/**
 * @author lexx
 */
public class BootstrapHeadContributor implements IHeaderContributor {

    private final Application application;

    public BootstrapHeadContributor(@Nonnull Application application) {
        this.application = application;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        ResourceReference jQueryReference = this.application.getJavaScriptLibrarySettings().getJQueryReference();
        response.render(JavaScriptHeaderItem.forReference(jQueryReference));
        response.render(JavaScriptHeaderItem.forUrl("js/bootstrap.js", "bootstrap-js"));
    }

}
