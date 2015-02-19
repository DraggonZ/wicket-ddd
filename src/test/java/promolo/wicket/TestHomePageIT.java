package promolo.wicket;

import org.apache.wicket.util.tester.WicketTester;
import org.jboss.weld.environment.se.Weld;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Simple test using the WicketTester
 */
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

    @Test(enabled = true)
    public void homepageRendersSuccessfully() {
        //start and render the test page
        this.tester.startPage(HomePage.class);
        //assert rendered page class
        this.tester.assertRenderedPage(HomePage.class);
    }

}
