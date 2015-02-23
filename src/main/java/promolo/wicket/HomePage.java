package promolo.wicket;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import promolo.wicket.account.application.AccountApplicationService;
import promolo.wicket.account.instractructure.wicket.AccountEditorPanel;
import promolo.wicket.core.ui.component.HideEmptyFeedbackPanelBehavior;
import promolo.wicket.core.ui.component.RefreshOnAjaxBehavior;

public class HomePage extends WebPage {

    @Inject
    private AccountApplicationService accountApplicationService;

    public HomePage(PageParameters parameters) {
        super(parameters);

        if (parameters.get("id").isEmpty()) {
            throw new RestartResponseException(HomePage.class, new PageParameters().add("id", "jdou"));
        }
        add(new PageFeedbackFragment("pageFeedbackPlaceholder", this));
        add(new AccountEditorPanel("accountEditorPanel", parameters.get("id").toString()));
    }

    private static final class PageFeedbackFragment extends Fragment {

        public PageFeedbackFragment(String id, MarkupContainer markupProvider) {
            super(id, "pageFeedbackFragment", markupProvider);

            setRenderBodyOnly(true);

            WebMarkupContainer pageFeedbackWrapper = new WebMarkupContainer("pageFeedbackWrapper");
            pageFeedbackWrapper.setOutputMarkupPlaceholderTag(true);
            pageFeedbackWrapper.add(new RefreshOnAjaxBehavior());

            final FencedFeedbackPanel pageFeedbackPanel = new FencedFeedbackPanel("pageFeedbackPanel");
            pageFeedbackPanel.add(new HideEmptyFeedbackPanelBehavior());
            pageFeedbackWrapper.add(pageFeedbackPanel);

            pageFeedbackWrapper.add(new Behavior() {

                @Override
                public void onConfigure(Component component) {
                    super.onConfigure(component);
                    pageFeedbackPanel.configure();
                    component.setVisible(pageFeedbackPanel.isVisible());
                }

            });

            add(pageFeedbackWrapper);
        }

    }

}
