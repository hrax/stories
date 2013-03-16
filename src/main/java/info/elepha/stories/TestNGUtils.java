package info.elepha.stories;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

public abstract class TestNGUtils {

	public static void screenshot(ITestResult result, WebDriver driver, String parent) {
		Reporter.setCurrentTestResult(result);
		String filename = "TEST-" + result.getTestClass().getName() + "." + result.getName() + ".png";
		WebDriverUtils.screenshot(driver, filename, parent);
		Reporter.log(String.format("<a href=\"%s\">error screenshot</a>", filename));
		Reporter.setCurrentTestResult(null);
	}
	
	public static void screenshotOnFailure(ITestResult result, WebDriver driver, String parent) {
		if (!result.isSuccess()) {
			screenshot(result, driver, parent);
		}
	}
	
}
