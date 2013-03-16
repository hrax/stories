package info.elepha.stories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class ScreenshotOnFailureListener extends AbstractWebDriverEventListener {
	private final String parent;
	public ScreenshotOnFailureListener(String parent) {
		this.parent = parent;
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		StackTraceElement trace = throwable.getStackTrace()[0];
		WebDriverUtils.screenshot(driver, "TEST-" + trace.getClassName() + "." + trace.getMethodName(), parent);
	}
}