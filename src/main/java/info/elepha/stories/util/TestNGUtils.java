package info.elepha.stories.util;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * TestNG util class
 * @author Gregor "hrax" Magdolen
 */
public abstract class TestNGUtils {

	/**
	 * Create screenshot of given {@code result}, {@code driver} and {@code parent} 
	 */
	public static void screenshot(ITestResult result, WebDriver driver, String parent) {
		Reporter.setCurrentTestResult(result);
		String filename = "TEST-" + result.getTestClass().getName() + "." + result.getName() + ".png";
		WebDriverUtils.screenshot(driver, filename, parent);
		Reporter.log(String.format("<a href=\"%s\">error screenshot</a>", filename));
		Reporter.setCurrentTestResult(null);
	}
	
	/**
	 * Create screenshot of given {@code result}, {@code driver} and {@code parent} 
	 * only if result is not successful
	 */
	public static void screenshotOnFailure(ITestResult result, WebDriver driver, String parent) {
		if (!result.isSuccess()) {
			screenshot(result, driver, parent);
		}
	}
	
}
