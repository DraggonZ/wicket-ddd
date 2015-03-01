package promolo.wicket;

import org.apache.wicket.util.tester.WicketTester;
import org.jboss.weld.environment.se.Weld;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import promolo.wicket.account.instractructure.presentation.AccountLayout;

public class TestHomePageIT {

    private final Weld weld = new Weld();

    private WicketTester tester;

    @BeforeClass
    public void setUpBeforeClass() {
        this.weld.initialize();
    }

    @AfterClass
    public void tearDownAfterClass() {
        this.weld.shutdown();
    }

    @BeforeMethod
    public void setUpBeforeMethod() {
        this.tester = new WicketTester(new WicketApplication());
    }

    @Test(enabled = false)
    public void homepageRendersSuccessfully() {
        this.tester.startPage(AccountLayout.class);
        this.tester.assertRenderedPage(AccountLayout.class);
    }

}
