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
