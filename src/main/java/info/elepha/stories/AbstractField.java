package info.elepha.stories;

import org.openqa.selenium.By;

public abstract class AbstractField {

	private final Form form;
	
	private final By selector;
	
	public AbstractField(Form form, By selector) {
		this.form = form;
		this.selector = selector;
		initialize();
	}
	
	protected final By getSelector() {
		return selector;
	}
	
	protected final Form getForm() {
		return form;
	}

	protected abstract void initialize();
	
}
