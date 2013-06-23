package info.elepha.stories.field;

import info.elepha.stories.AbstractField;
import info.elepha.stories.Form;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextField extends AbstractField {

	private WebElement element;
	
	public TextField(Form form, By selector) {
		super(form, selector);
	}

	@Override
	protected void initialize() {
		element = getForm().findElement(getSelector());
		if (!("textarea".equalsIgnoreCase(element.getTagName())) || !("input".equalsIgnoreCase(element.getTagName()) && "text".equalsIgnoreCase(element.getAttribute("type")))) {
			element = null;
			throw new IllegalStateException("Given selector does not select element of textarea or input of type text.");
		}
	}
	
	public void type(CharSequence text) {
		element.sendKeys(text);
	}

}
