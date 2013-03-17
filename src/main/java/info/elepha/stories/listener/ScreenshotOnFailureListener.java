package info.elepha.stories.listener;

import info.elepha.stories.util.WebDriverUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

/**
 * WebDriver listener to create screenshot on exception 
 * @author Gregor "hrax" Magdolen
 */
public class ScreenshotOnFailureListener extends AbstractWebDriverEventListener {
	private final String parent;
	
	/**
	 * Create new listener with given {@code parent} as the root folder for 
	 * storing screenshots
	 * @param parent the root folder where to store the screenshot to
	 */
	public ScreenshotOnFailureListener(String parent) {
		this.parent = parent;
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		StackTraceElement trace = throwable.getStackTrace()[0];
		WebDriverUtils.screenshot(driver, "TEST-" + trace.getClassName() + "." + trace.getMethodName(), parent);
	}
}