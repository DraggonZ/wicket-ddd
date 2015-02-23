package promolo.wicket.core.ui.model;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.wicket.model.IModel;
import org.bindgen.Binding;
import org.bindgen.BindingRoot;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public final class Bindgen {

    @Nonnull
    public static <T> IModel<T> modelOf(@Nonnull Binding<T> binding) {
        return new BindgenComponentAssignedModel<>(binding);
    }

    @Nonnull
    public static <R, T> IModel<T> modelOf(@Nonnull IModel<? extends R> root, @Nonnull BindingRoot<R, T> binding) {
        return new DefaultBindgenModel<>(root, binding);
    }

    @Nonnull
    public static <R extends Serializable, T> IModel<T> modelOf(@Nonnull R root, @Nonnull BindingRoot<R, T> binding) {
        return new DefaultBindgenModel<>(root, binding);
    }

    private Bindgen() {
        // nop
    }

}
