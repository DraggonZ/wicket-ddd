package promolo.wicket.account.ui.toolbar;

import java.io.Serializable;

import javax.annotation.Nonnull;

import promolo.wicket.account.ui.list.AccountRow;

/**
 * TODO javadoc
 *
 * @author Александр
 */
public interface AccountToolbarView extends Serializable {

    public void updateControlPanel();

    public void notifyAccountWasRemoved(@Nonnull AccountRow accountRow);

}
