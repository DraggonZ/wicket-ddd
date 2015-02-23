package promolo.wicket.account.ui;

import java.io.Serializable;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountView extends Serializable {

    public void accountPersonChanged();

    public void accountTitleGenerated();

    public void titleAutoGeneratorStateChanged(boolean enabled);

}
