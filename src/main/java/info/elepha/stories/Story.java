package info.elepha.stories;

import info.elepha.stories.util.WebDriverUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

/**
 * The utility class that allow to write readable {@link WebDriver} tests as one 
 * would be telling a story.
 * @author Gregor "hrax" Magdolen
 */
public class Story {

	private final WebDriver driver;
	
	private int readyTimeout = 10;
	
	/**
	 * Create new Story for given {@code driver}
	 * @param driver the driver for this story
	 */
	public Story(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Create new Story for given {@code driver} and {@code readyTimeout}
	 * @param driver the driver for this story
	 * @param readyTimeout the default ready timeout for this story
	 */
	public Story(WebDriver driver, int readyTimeout) {
		this(driver);
		this.readyTimeout = readyTimeout;
	}
	
	/**
	 * Create new Story for given {@code driver} and load given {@code url}
	 * @param driver the driver for this story
	 * @param url the url to start this story with
	 */
	public Story(WebDriver driver, String url) {
		this(driver);
		visit(url);
	}
	
	/**
	 * Create new Story for given {@code driver} and {@code readyTimeout} and load given {@code url}
	 * @param driver the driver for this story
	 * @param readyTimeout the default ready timeout for this story
	 * @param url the url to start this story with
	 */
	public Story(WebDriver driver, int readyTimeout, String url) {
		this(driver, readyTimeout);
		visit(url);
	}
	
	/**
	 * @return the driver for this story
	 */
	public WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * @return the default ready timeout for this story
	 */
	public int getReadyTimeout() {
		return readyTimeout;
	}
	
	/**
	 * Maximize story window
	 * @return current Story
	 */
	public Story maximize() {
		getDriver().manage().window().maximize();
		return this;
	}
	
	/**
	 * Visit given {@code url} and wait until page is {@link #ready()}
	 * @param url the url to visit
	 * @return current Story
	 * @see #ready()
	 */
	public Story visit(String url) {
		getDriver().get(url);
		return ready();
	}
	
	/**
	 * If given {@code selector} is a text entry element (input or textarea), this will clear the value. Has no effect on other elements.
	 * @param selector the element selector
	 * @return current Story
	 */
	public Story clear(By selector) {
		WebDriverUtils.clear(getDriver(), selector);
		return this;
	}
	
	/**
	 * Clicks on given {@code selector}. If this causes to load new page, the method 
	 * tries to block until the page is loaded, however it is recommended to  
	 * call {@link #ready()} afterwards.
	 * @param selector the element selector
	 * @return current Story
	 */
	public Story click(By selector) {
		WebDriverUtils.click(getDriver(), selector);
		return this;
	}
	
	/**
	 * Performs context click on given {@code selector}
	 * @param selector the element selector
	 * @return current Story
	 */
	public Story context(By selector) {
		WebDriverUtils.context(getDriver(), selector);
		return this;
	}
	
	/**
	 * Use this method to simulate typing into a {@code selector}, which may set its value.
	 * @param selector the element selector
	 * @param text the text to type
	 * @return current Story
	 */
	public Story type(By selector, CharSequence... text) {
		WebDriverUtils.type(getDriver(), selector);
		return this;
	}
	
	/**
	 * If given {@code selector} is a form, or an element within a form, then this will be submitted to
	 * the remote server. If this causes the current page to change, then this method will block until
	 * the new page is loaded.
	 * @param selector the element selector
	 * @return current Story
	 */
	public Story submit(By selector) {
		WebDriverUtils.submit(getDriver(), selector);
		return this;
	}
	
	/**
	 * Use this method to simulate typing into a {@code selector} and if it is in form 
	 * element submit it to the remote server. If this causes the current page 
	 * to change, then this method will block until the new page is loaded. 
	 * @param selector the element selector
	 * @param text the text to type
	 * @return current Story
	 */
	public Story typeAndSubmit(By selector, CharSequence... text) {
		return type(selector, text).submit(selector);
	}
	
	/**
	 * Wait until given {@code predicate} returns true
	 * @param timeout the timeout for wait
	 * @param p the predicate to run
	 * @return current Story
	 */
	public Story wait(int timeout, Predicate<WebDriver> predicate) {
		WebDriverUtils.wait(getDriver(), timeout).until(predicate);
		return this;
	}

	/**
	 * Wait until the page is ready (loaded) with default ready timeout
	 * @return current Story
	 * @see #getReadyTimeout()
	 */
	public Story ready() {
		return ready(getReadyTimeout());
	}
	
	/**
	 * Wait until the page is ready (loaded) using given {@code timeout}
	 * @param timeout the timeout to wait
	 * @return current Story
	 */
	public Story ready(int timeout) {
		WebDriverUtils.ready(getDriver(), timeout);
		return this;
	}
	
	/**
	 * Switch story to frame with given {@code nameOrId}
	 * @param nameOrId the name or id of the frame to switch to
	 * @return current Story
	 */
	public Story switchToFrame(String nameOrId) {
		WebDriverUtils.switchToFrame(getDriver(), nameOrId);
		return this;
	}

	/**
	 * Switch story to frame with given {@code selector}
	 * @param selector the frame element selector
	 * @return current Story
	 */
	public Story switchToFrame(By selector) {
		WebDriverUtils.switchToFrame(getDriver(), selector);
		return this;
	}

	/**
	 * Switch story to default content (to blur focused frame)
	 * @return current Story
	 */
	public Story switchToDefaultContent() {
		WebDriverUtils.switchToDefaultContent(getDriver());
		return this;	
	}
	
	/**
	 * Verify given {@code verification}
	 * @param verification the verification to verify
	 * @return current Story
	 */
	public Story verify(Verification verification) {
		verification.verify(getDriver());
		return this;
	}
	
	/**
	 * Perform given {@code action}
	 * @param action the action to perform
	 * @return current Story
	 */
	public Story perform(Action action) {
		action.perform(driver);
		return this;
	}
	
	/**
	 * End current story
	 */
	public void end() {
		getDriver().close();
	}
	
	/**
	 * The action to be performed in the story - e.g. login, logout or create 
	 * new article
	 * @author Gregor "hrax" Magdolen
	 */
	public interface Action {
		/**
		 * Perform this method with given {@code driver}
		 * @param driver
		 */
		public void perform(WebDriver driver);
	}
	
	/**
	 * The verification to be performed in the story - e.g. after login or creating 
	 * new article
	 * @author Gregor "hrax" Magdolen
	 */
	public interface Verification {
		/**
		 * Verify this method with given {@code driver}
		 * @param driver
		 */
		public void verify(WebDriver driver);
	}
	
}
