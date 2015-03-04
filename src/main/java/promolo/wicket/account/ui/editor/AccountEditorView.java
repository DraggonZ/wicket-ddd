package promolo.wicket.account.ui.editor;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountEditorView extends Serializable {

    public void showEditor(@Nonnull AccountEditModel accountEditModel);

    public void closeEditor();

}
