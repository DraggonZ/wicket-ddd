package promolo.wicket.core.ui.model;

import javax.annotation.Nonnull;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.bindgen.BindingRoot;

/**
 * @author amorozov
 */
public final class DefaultBindgenModel<R, T> extends AbstractBindgenModel<T> {

    private final Object root;

    public DefaultBindgenModel(@Nonnull IModel<? extends R> root, @Nonnull BindingRoot<R, T> binding) {
        super(binding);
        this.root = root;
    }

    public DefaultBindgenModel(@Nonnull R root, @Nonnull BindingRoot<R, T> binding) {
        super(binding);
        this.root = root;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final R getRootObject() {
        if (this.root instanceof IModel) {
            return ((IModel<? extends R>) this.root).getObject();
        } else {
            return (R) this.root;
        }
    }

    public void detach() {
        if (this.root instanceof IDetachable) {
            ((IDetachable) this.root).detach();
        }
    }

}