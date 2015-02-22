package promolo.wicket.core.ui.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.IPropertyReflectionAwareModel;
import org.bindgen.Binding;
import org.bindgen.BindingRoot;

/**
 * @author amorozov
 */
public abstract class AbstractBindgenModel<T> implements IPropertyReflectionAwareModel<T>, IObjectClassAwareModel<T> {

    private final Binding<T> binding;

    protected AbstractBindgenModel(@Nonnull Binding<T> binding) {
        this.binding = binding;
    }

    @Nonnull
    public final BindingRoot<Object, T> getBinding() {
        return (BindingRoot<Object, T>) binding;
    }

    @Override
    public T getObject() {
        return getBinding().getSafelyWithRoot(getRootObject());
    }

    @Override
    public void setObject(T object) {
        getBinding().setWithRoot(getRootObject(), object);
    }

    protected abstract Object getRootObject();

    @Override
    public final Field getPropertyField() {
        return PropertyResolver.getPropertyField(getBinding().getPath(), getRootObject());
    }

    @Override
    public final Method getPropertyGetter() {
        return PropertyResolver.getPropertyGetter(getBinding().getPath(), getRootObject());
    }

    @Override
    public final Method getPropertySetter() {
        return PropertyResolver.getPropertySetter(getBinding().getPath(), getRootObject());
    }

    @Override
    public final Class<T> getObjectClass() {
        return (Class<T>) getBinding().getType();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("bindingType", getBinding().getType().getName());
        builder.append("bindingPath", getBinding().getPath());
        return builder.toString();
    }

}