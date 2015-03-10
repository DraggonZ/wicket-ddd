package promolo.wicket.core.ui.application;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInitializationListener;
import org.apache.wicket.util.collections.ClassMetaCache;

import promolo.wicket.core.stereotype.PresenterInstance;
import promolo.wicket.core.ui.notification.ViewUserEventListener;

/**
 * TODO javadoc
 *
 * @author lexx
 */
public final class PresenterInstancePostProcessor implements IComponentInitializationListener {

    private static final String PACKAGE_DELIMITER = ".";

    private final ClassMetaCache<List<Field>> presenterFieldCache = new ClassMetaCache<>();

    private final Set<String> excludePackagePrefixes = new HashSet<>();

    public PresenterInstancePostProcessor() {
        addExcludePackage("org.apache.wicket.");
    }

    public void addExcludePackage(@Nonnull String prefix) {
        this.excludePackagePrefixes.add(StringUtils.appendIfMissing(prefix, PACKAGE_DELIMITER, PACKAGE_DELIMITER));
    }

    @Override
    public void onInitialize(Component component) {
        if (!isExcluded(component)) {
            List<Field> cachedFieldList = this.presenterFieldCache.get(component.getClass());
            if (cachedFieldList == null) {
                Field[] allFields = FieldUtils.getAllFields(component.getClass());
                List<Field> fields = new ArrayList<>(1);
                for (Field field : allFields) {
                    if (field.getAnnotation(PresenterInstance.class) != null) {
                        fields.add(field);
                    }
                }
                cachedFieldList = (fields.isEmpty() ? Collections.<Field>emptyList() : fields);
                this.presenterFieldCache.put(component.getClass(), cachedFieldList);
            }
            postProcessPresenterInstances(component, cachedFieldList);
        }
    }

    private boolean isExcluded(@Nonnull Component component) {
        String packageName = component.getClass().getPackage().getName();
        for (String excludePackagePrefix : this.excludePackagePrefixes) {
            if (packageName.startsWith(excludePackagePrefix)) {
                return true;
            }
        }
        return false;
    }

    private static void postProcessPresenterInstances(@Nonnull Component component, @Nonnull List<Field> fields) {
        for (Field field : fields) {
            postProcessPresenterInstance(component, field);
        }
    }

    private static void postProcessPresenterInstance(@Nonnull Component component, @Nonnull Field field) {
        boolean resetAccessibleFlag = false;
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
                resetAccessibleFlag = true;
            }
            Object presenter = field.get(component);
            if (presenter instanceof Serializable) {
                component.add(new ViewUserEventListener((Serializable) presenter));
            }
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("ошибка получения доступа к полю " + field, ex);
        } finally {
            if (resetAccessibleFlag) {
                field.setAccessible(false);
            }
        }
    }

}
