package promolo.wicket.core.ui.component;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.IAjaxRegionMarkupIdProvider;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class RefreshOnAjaxBehavior extends Behavior implements IAjaxRegionMarkupIdProvider {

    public static final RefreshOnAjaxBehavior INSTANCE = new RefreshOnAjaxBehavior();

    public RefreshOnAjaxBehavior() {
        super();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        if (!component.getOutputMarkupId()) {
            component.setOutputMarkupPlaceholderTag(true);
        }
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        if (event.getPayload() instanceof AjaxRequestHandler) {
            ((AjaxRequestHandler) event.getPayload()).add(component);
        }
    }

    @Override
    public String getAjaxRegionMarkupId(Component component) {
        return null;
    }

}
