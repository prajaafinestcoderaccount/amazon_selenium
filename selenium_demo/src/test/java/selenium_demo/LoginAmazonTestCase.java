package selenium_demo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import POM.LoginPOM;

public class LoginAmazonTestCase {
	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in/ax/claim?openid.mode=checkid_setup&openid.assoc_handle=inflex&arb=a9a879a4-680d-4249-ab49-ae90629fccc6&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.return_to=https%3A%2F%2Fwww.amazon.in%2F%3Fref_%3Dnav_youraccount_switchacct&policy_handle=Retail-Checkout");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
	}
	
	@DataProvider(name="credentials")
	public Object[][] getData(){
		return new Object[][] {
			{"hariprajaa05@gmail.com","shreshta"}, //valid
			{"",""},//invalid
			{"meow","meow"}//invalid
		};
	}
	@Test(dataProvider="credentials")
	public void Login(String username,String password) {
		LoginPOM ls = new LoginPOM(driver);
		ls.Email_value(username);
		ls.Click_continue_button();
		ls.Password_value(password);
		ls.Click_signin_button();
		
		
	}
	
}