package info.elepha.stories.field;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import info.elepha.stories.AbstractField;
import info.elepha.stories.Form;

public class CheckboxField extends AbstractField {

	private WebElement element;
	
	public CheckboxField(Form form, By selector) {
		super(form, selector);
	}

	@Override
	protected void initialize() {
		element = getForm().findElement(getSelector());
		if (!("input".equalsIgnoreCase(element.getTagName()) && "checkbox".equalsIgnoreCase(element.getAttribute("type")))) {
			element = null;
			throw new IllegalStateException("Given selector does not select input field with type checkbox.");
		}
	}

	public boolean isChecked() {
		return element.isSelected();
	}
	
	public void check() {
		if (!isChecked())
			element.click();
	}
	
	public void uncheck() {
		if (isChecked()) {
			element.click();
		}
	}
	
}
