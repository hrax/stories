package info.elepha.stories;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import info.elepha.stories.field.CheckboxField;
import info.elepha.stories.field.TextField;

public class Form {

	private final WebDriver driver;
	
	private final By selector;
	
	private final WebElement element;
	
	public Form (WebDriver driver, By selector) {
		this.driver = driver;
		this.selector = selector;
		this.element = driver.findElement(selector);
	}
	
	public final WebDriver getDriver() {
		return driver;
	}
	
	public final By getSelector() {
		return selector;
	}
	
	public final WebElement getElement() {
		return element;
	}
	
	public final WebElement findElement(By selector) {
		return element.findElement(selector);
	}

	public final List<WebElement> findElements(By selector) {
		return element.findElements(selector);
	}
	
	private By getFielNameSelector(String name) {
		return By.xpath("*[@name='"+name+"']");
	}
	
	public void type(String name, CharSequence text) {
		new TextField(this, getFielNameSelector(name)).type(text);
	}
	
	public void check(String name) {
		new CheckboxField(this, getFielNameSelector(name)).check();
	}
	
	public void uncheck(String name) {
		new CheckboxField(this, getFielNameSelector(name)).check();
	}
	
}
