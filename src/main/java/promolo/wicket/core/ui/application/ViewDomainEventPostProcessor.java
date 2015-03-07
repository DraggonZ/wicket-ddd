package promolo.wicket.core.ui.application;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInstantiationListener;
import promolo.wicket.core.ui.component.ViewDomainEventListener;

/**
 * @author lexx
 */
public class ViewDomainEventPostProcessor implements IComponentInstantiationListener {

    @Override
    public void onInstantiation(Component component) {
        if (component instanceof Page) {
            component.add(new ViewDomainEventListener());
        }
    }

}
