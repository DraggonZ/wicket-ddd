package promolo.wicket.core.ui.component;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public class HideEmptyComponent extends Behavior {

    public HideEmptyComponent() {
        super();
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        component.setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        Object modelObject = component.getDefaultModelObject();
        if (modelObject instanceof CharSequence) {
            component.setVisible(StringUtils.isNotEmpty((CharSequence) modelObject));
        } else {
            component.setVisible(modelObject != null);
        }
    }

}
