package promolo.wicket.core.ui.model;

import javax.annotation.Nonnull;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.bindgen.Binding;

/**
 * @author amorozov
 */
public class BindgenComponentAssignedModel<T> extends AbstractReadOnlyModel<T> implements IComponentAssignedModel<T> {

    private final Binding<T> binding;

    public BindgenComponentAssignedModel(@Nonnull Binding<T> binding) {
        super();
        this.binding = binding;
    }

    @Override
    public final T getObject() {
        throw new IllegalStateException("данный метод не должен использоваться: нужно использовать Wrapper-модель");
    }

    @Override
    public IWrapModel<T> wrapOnAssignment(Component component) {
        return new AssignedModel<>(component, this.binding);
    }

    private final class AssignedModel<P> extends AbstractBindgenModel<P> implements IWrapModel<P> {

        private final Component component;

        public AssignedModel(@Nonnull Component component, @Nonnull Binding<P> binding) {
            super(binding);
            this.component = component;
        }

        @Override
        protected Object getRootObject() {
            IModel<?> model = this.component.getParent().getInnermostModel();
            if (model == null) {
                throw new IllegalStateException("модель не найдена для компоненты " + this.component);
            }
            return model.getObject();
        }

        @Override
        public IModel<?> getWrappedModel() {
            return BindgenComponentAssignedModel.this;
        }

        @Override
        public void detach() {
            BindgenComponentAssignedModel.this.detach();
        }

    }

}
