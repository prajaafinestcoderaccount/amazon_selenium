package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPOM {
	WebDriver driver;
	
	private static Logger logger = LogManager.getLogger(LoginPOM.class);
	
	//constructor
	
	
	public LoginPOM(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	//Locators
	//solves duplicate problem and if changes we dont have to change every single place.
	@FindBy(id="ap_email_login")
	WebElement Email;

	
	@FindBy(xpath="//input[@type='submit']")
	WebElement continue_button;
	
	@FindBy(id="ap_password")
	WebElement password;
	
	@FindBy(xpath="//input[@id='signInSubmit']")
	WebElement signin_button;
	
	//Action 
	
	public void Email_value(String email) {
		Email.sendKeys(email);
		logger.info("email");
	}
	public void Click_continue_button() {
		continue_button.click();
		logger.info("clicked continue");
	}
	public void Password_value(String pass) {
		password.sendKeys(pass);
		logger.info("password field");
	}
	public void Click_signin_button() {
		signin_button.click();
		logger.info("signin clicked");
	}
	
	
	
}