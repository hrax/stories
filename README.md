Stories
=======

Simple and easy way how to write WebDriver tests - with Stories!

The motion behind this project is to simplify writing of WebDriver tests. WebDriver itself is useful tool but most of the time it requires a lot of writing to perform simple operations.

Tests are most of the time supplied in a form of a user story (sentence). This tools allows to rewrite the story into a test with necessity knowing only the selector of the element.

Example
-------

A simple user story:

> User John Doe enters administration site, logs in and publishes new article.

would be transformed as follows:

> new Story(webdriver, "http://www.example.com/admin")  
> &nbsp;&nbsp;&nbsp;&nbsp;.perform(new LoginAction("jdoe", "password")  
> &nbsp;&nbsp;&nbsp;&nbsp;.ready()  
> &nbsp;&nbsp;&nbsp;&nbsp;.click(By.xpath("a[text()='Create article']"))  
> &nbsp;&nbsp;&nbsp;&nbsp;.ready()  
> &nbsp;&nbsp;&nbsp;&nbsp;.perform(new CreateArticleAction())  
> &nbsp;&nbsp;&nbsp;&nbsp;.ready()  
> &nbsp;&nbsp;&nbsp;&nbsp;.verify(new Story.Verification() {  
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void verify(WebDriver driver) {  
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// perform verification that article was created succesfully  
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}  
> &nbsp;&nbsp;&nbsp;&nbsp;});



Most of the UI tests come down to filling forms. Stories offer simple way not only to handle and submit them, but also to extend them to your own implementation.

To create new form you will need a WebDriver instance and selector for the form.

> Form form = new Form(driver, By.xpath("form[@id='login']"));

To operate basic elements you will need name of the field and perform appropriate action on the form instance. 

> form.type(fieldname, text);
> 
> form.check(fieldname); // single checkbox
> form.uncheck(fieldname); // single checkbox
> 
> form.check(fieldname, value); // group of checkboxes or radio buttons
> form.uncheck(fieldname, value); // group of checkboxes
> 
> form.select(fieldname, option);
> 
> form.click(By); // to test label connections to right fields or to click on form button
> 
> form.submit(); // submit by using first submit button available
> form.submit(name); // submit by submit button name;
> 
> form.reset(); // reset form using form reset button if available otherwise throws exception
> 
> form.field(By, Class<? extends AbstractField>); // retrieve special implementation of the form field


