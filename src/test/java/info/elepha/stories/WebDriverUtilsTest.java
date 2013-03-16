package info.elepha.stories;

import static org.junit.Assert.assertTrue;
import info.elepha.stories.listener.ScreenshotOnFailureListener;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverUtilsTest {

	@Test
	public void testOfScreenshotOnFailureListener() throws Exception {
		String filename = "TEST-" + WebDriverUtilsTest.class.getCanonicalName() + ".testOfScreenshotOnFailureListener.png";
		URL resource = getClass().getResource(getClass().getSimpleName() + ".class");
		String parent = new File(resource.toURI()).getParentFile().getAbsolutePath();
		
		FirefoxDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://www.elepha.info/my-photos");
		
		ScreenshotOnFailureListener listener = new ScreenshotOnFailureListener(parent);
		listener.onException(new Throwable(), driver);
		driver.close();
		assertTrue(new File(parent, filename).exists());
	}
	
}
