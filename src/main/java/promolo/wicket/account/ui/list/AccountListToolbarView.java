package promolo.wicket.account.ui.list;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountListToolbarView extends Serializable {

    public void updateControlPanel();

    public void notifyAccountWasRemoved(@Nonnull AccountRow accountRow);

}
