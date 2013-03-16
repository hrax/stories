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

public abstract class WebDriverUtils {
	
	public static final int DEFAULT_TIMEOUT = 10;
	
	public static void click(WebDriver driver, By by) {
		driver.findElement(by).click();
	}
	
	public static void context(WebDriver driver, By by) {
		new Actions(driver).contextClick(driver.findElement(by)).build().perform();
	}
	
	public static void clear(WebDriver driver, By by) {
		driver.findElement(by).clear();
	}
	
	public static void submit(WebDriver driver, By by) {
		driver.findElement(by).submit();
	}
	
	public static void type(WebDriver driver, By by, CharSequence... keys) {
		click(driver, by);
		driver.findElement(by).sendKeys(keys);
	}
	
	public static FluentWait<WebDriver> wait(WebDriver driver, int timeout) {
		return new WebDriverWait(driver, timeout);
	}
	
	public static void until(WebDriver driver, int timeout, Predicate<WebDriver> predicate) {
		wait(driver, timeout).until(predicate);
	}

	public static <V> V execute(WebDriver driver, int timeout, Function<WebDriver, V> function) {
		return wait(driver, timeout).until(function);
	}
	
	public static void ready(WebDriver driver, int timeout) {
		until(driver, timeout, new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				return "complete".equals(((JavascriptExecutor)driver).executeScript("return document.readyState"));
			}
		});
	}
	
	public static void switchToFrame(WebDriver driver, String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	public static void switchToFrame(WebDriver driver, By by) {
		driver.switchTo().frame(driver.findElement(by));
	}
	
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public static byte[] screenshot(WebDriver driver) {
		if (!TakesScreenshot.class.isAssignableFrom(driver.getClass())) {
			return null;
		}
		return TakesScreenshot.class.cast(driver).getScreenshotAs(OutputType.BYTES);
	}
	
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
