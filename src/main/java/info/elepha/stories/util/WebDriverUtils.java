package info.elepha.stories.util;

import java.io.File;
import java.io.FileOutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * WebDriver utility class. Shortens very most of the used calls.
 * @author Gregor "hrax" Magdolen
 */
public abstract class WebDriverUtils {
	
	/**
	 * The default timeout; by default meant for second timers
	 */
	public static final int DEFAULT_TIMEOUT = 10;
	
	/**
	 * @param driver the WebDriver to click in
	 * @param selector the selector of the element to click on
	 */
	public static void click(WebDriver driver, By selector) {
		driver.findElement(selector).click();
	}
	
	/**
	 * @param driver the WebDriver to context click in
	 * @param selector the selector of the element to context click on
	 */
	public static void context(WebDriver driver, By selector) {
		new Actions(driver).contextClick(driver.findElement(selector)).build().perform();
	}
	
	/**
	 * @param driver the WebDriver
	 * @param selector the selector of the element to be cleared
	 */
	public static void clear(WebDriver driver, By selector) {
		driver.findElement(selector).clear();
	}
	
	/**
	 * @param driver
	 * @param selector
	 */
	public static void submit(WebDriver driver, By selector) {
		driver.findElement(selector).submit();
	}
	
	/**
	 * @param driver
	 * @param selector
	 * @param keys
	 */
	public static void type(WebDriver driver, By selector, CharSequence... keys) {
		click(driver, selector);
		driver.findElement(selector).sendKeys(keys);
	}
	
	/**
	 * @param driver
	 * @param timeout
	 * @return new FluentWait instance
	 */
	public static FluentWait<WebDriver> wait(WebDriver driver, int timeout) {
		return new WebDriverWait(driver, timeout);
	}
	
	/**
	 * @param driver
	 * @param timeout
	 * @param predicate
	 */
	public static void until(WebDriver driver, int timeout, Predicate<WebDriver> predicate) {
		wait(driver, timeout).until(predicate);
	}

	/**
	 * @param driver
	 * @param timeout
	 * @param function
	 * @return the value of the function
	 */
	public static <V> V execute(WebDriver driver, int timeout, Function<WebDriver, V> function) {
		return wait(driver, timeout).until(function);
	}
	
	/**
	 * @param driver
	 * @param timeout
	 */
	public static void ready(WebDriver driver, int timeout) {
		until(driver, timeout, new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				return "complete".equals(((JavascriptExecutor)driver).executeScript("return document.readyState"));
			}
		});
	}
	
	/**
	 * @param driver
	 * @param nameOrId
	 */
	public static void switchToFrame(WebDriver driver, String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	/**
	 * @param driver
	 * @param selector
	 */
	public static void switchToFrame(WebDriver driver, By selector) {
		driver.switchTo().frame(driver.findElement(selector));
	}
	
	/**
	 * @param driver
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	/**
	 * @param driver
	 * @return screenshot in bytes
	 */
	public static byte[] screenshot(WebDriver driver) {
		if (!TakesScreenshot.class.isAssignableFrom(driver.getClass())) {
			return null;
		}
		return TakesScreenshot.class.cast(driver).getScreenshotAs(OutputType.BYTES);
	}
	
	/**
	 * @param driver
	 * @param filename
	 * @param parent
	 */
	public static void screenshot(WebDriver driver, String filename, String parent) {
		byte[] screenshot = screenshot(driver);
		if (screenshot == null) {
			throw new IllegalStateException(String.format("Driver %s does not support tanking screenshots.", driver.getClass().getName()));
		}
		try {
			File file = new File(parent, filename);
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream os = new FileOutputStream(file);
			os.write(screenshot);
			os.close();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save screenshot...", e);
		}
	}
	
}
