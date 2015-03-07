package promolo.wicket.account.ui.editor;

import java.io.Serializable;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountEditorView extends Serializable {

    public void openEditor();

    public void updateEditor();

    public void closeEditor();

}
