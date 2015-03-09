package promolo.wicket.account.ui.editor;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountEditorView extends Serializable {

    public void openEditor(@Nonnull AccountEditModel accountEditModel);

    public void updateEditor(@Nonnull AccountEditModel accountEditModel);

    public void closeEditor();

}
