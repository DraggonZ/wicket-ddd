package promolo.wicket.account.ui;

import java.io.Serializable;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountLayoutView extends Serializable {

    public void newAccountEditor();

    public void showAccountEditor(String id);

    public void updateAccountSelector(String id);

    public void updateAccountList();

    public void updateControlPanel(String id);
    
}
