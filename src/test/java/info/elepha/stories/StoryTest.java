package info.elepha.stories;

import static org.junit.Assert.assertEquals;
import info.elepha.stories.util.TestNGUtils;
import info.elepha.stories.util.WebDriverUtils;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;

@SuppressWarnings ("javadoc")
public class StoryTest {

	private String parent;
	
	private WebDriver driver;
	
	@BeforeClass
	public void onStart(ITestContext context) throws Exception {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		parent = context.getOutputDirectory();
	}

	@AfterClass
	public void onEnd() {
		driver.quit();
	}
	
	@AfterMethod
	public void afterTest(ITestResult result) {
		TestNGUtils.screenshotOnFailure(result, driver, parent);
	}
	
	@Test
	public void testTellingStory() throws Exception {
		new Story(driver, "http://www.elepha.info")
			.click(By.xpath("//a[text()='My Notebook']"))
			.ready()
			.click(By.xpath("//a[contains(text(), 'Velocity')]"))
			.ready()
			.verify(new Story.Verification() {
				@Override
				public void verify(WebDriver driver) {
					assertEquals(2, driver.findElements(By.xpath("//section[@class='content']//footer//a[contains(text(), 'velocity')]")).size());
				}
			});
	}
	
	@Test
	public void testOfTellingSearchStory() throws Exception {
		new Story(driver, "http://www.elepha.info")
			.typeAndSubmit(By.xpath("id('cse-search')//input[@type='text']"), "IE overflow")
			.ready()
			.verify(new Story.Verification() {
				@Override
				public void verify(WebDriver driver) {
					Assert.assertThat(driver.findElement(By.xpath("//div[contains(@class, 'gsc-result')]//div[contains(@class, 'gs-snippet')]")).getText(), CoreMatchers.not(CoreMatchers.is("No Results")));
				}
			});
	}
	
	@Test
	public void testOfTellingStoryWithAction() throws Exception {
		new Story(driver, "http://www.google.com")
			.verify(new Story.Verification() {
				@Override
				public void verify(WebDriver driver) {
					Assert.assertThat(driver.findElement(By.tagName("body")).getText().contains("advanced"), CoreMatchers.is(false));
				}
			})
			.visit("http://www.google.com/advanced_search")
			.perform(new Story.Action() {
				@Override
				public void perform(WebDriver driver) {
					WebDriverUtils.type(driver, By.xpath("//form[@name='f']//input[@name='as_q']"), "IE 7 overflow bug");
					WebDriverUtils.click(driver, By.xpath("//form[@name='f']//div[@id='lr_button']"));
					WebDriverUtils.until(driver, 10, new Predicate<WebDriver>(){
						public boolean apply(WebDriver driver) {
							return !"none".equalsIgnoreCase(driver.findElement(By.xpath("//ul[@id='lr_menu']")).getCssValue("display"));
						}
					});
					WebDriverUtils.click(driver, By.xpath("//ul[@id='lr_menu']/li/div[text()='English']"));
					WebDriverUtils.click(driver, By.xpath("//form[@name='f']//input[@type='submit']"));
				}
			})
			.ready()
			.verify(new Story.Verification() {
				@Override
				public void verify(WebDriver driver) {
					Assert.assertThat(driver.findElement(By.xpath("//li[@class='g']//div[@class='f_kv']/cite[contains(text(), 'elepha.info')]")), CoreMatchers.notNullValue());
				}
			});
	}
	
}
