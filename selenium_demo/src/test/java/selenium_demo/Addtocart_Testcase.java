package selenium_demo;

import java.time.Duration;
import org.testng.Assert;
import org.testng.ITestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import POM.LoginPOM;
import POM.SearchPOM;

@Test(retryAnalyzer = selenium_demo.RetryAnalyzer.class)
public class Addtocart_Testcase {
	//Do login using LoginPOM and search using SearchPOM
	WebDriver driver;
	ExtentReports extent;
	ExtentTest test;

	private static Logger logger = LogManager.getLogger(Addtocart_Testcase.class);
	@BeforeSuite
	//extent report use this then line 64
	public void setupReport() {
		String reportPath = System.getProperty("user.dir") + "/reports/Amazon_AddToCart_Report.html";
		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

		spark.config().setDocumentTitle("Amazon Automation Test Report");
		spark.config().setReportName("Add To Cart Test Execution Report");
		spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Tester", "Hariprajaa");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Browser", "Chrome");
		logger.info("✅ Extent report initialized");
	}
	@BeforeTest
	void setup() {
		driver=new ChromeDriver();

		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		WebElement website = driver.findElement(By.id("APjFqb"));
		website.sendKeys("amazon.in",Keys.ENTER);
		WebElement open = driver.findElement(By.xpath("//div[@class='GUyUUb']//div//div[@class='uEierd']//div//div//span[contains(text(),'Shop online at Amazon India')]"));
		open.click();
		logger.info("opened amazon");
	}

	@Test
	public void Addingtocart() {
		test = extent.createTest("Add to Cart Test");

		try {
			// Login section LoginPOM 
			WebElement login_module = driver.findElement(By.xpath("//span[normalize-space()='Account & Lists']"));
			login_module.click();
			test.info("Clicked login module");

			LoginPOM ls = new LoginPOM(driver);
			ls.Email_value("hariprajaa05@gmail.com");
			ls.Click_continue_button();
			ls.Password_value("shreshta13@");
			ls.Click_signin_button();
			test.info("Entered login credentials");
			test.pass("Logged in successfully");

			// Search section SearchPOM
			SearchPOM search = new SearchPOM(driver);
			search.search_field("samsung galaxy s25 ultra");
			test.info("Searched for product");
			test.pass("Search implemented sucessfully");

			// Add to cart
			WebElement add_button = driver.findElement(By.id("a-autoid-3-announce"));
			add_button.click();
			test.info("Clicked add to cart button");

			// Validate cart count
			WebElement cart_count = driver.findElement(By.xpath("//span[@id='nav-cart-count']"));
			int cart_value = Integer.parseInt(cart_count.getText());

			Assert.assertTrue(cart_value > 0, "Item added successfully");
			test.pass("✅ Item successfully added to cart");
			
			//go to cart
			WebElement cart_button = driver.findElement(By.xpath("//span[@class='nav-cart-icon nav-sprite']"));
			cart_button.click();
			
			//click buy button
			WebElement Buy_button = driver.findElement(By.xpath("//input[@name='proceedToRetailCheckout']"));
			Buy_button.click();
			
			//radio button
			WebElement Radio_button = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[2]/div[1]/div[9]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/span[1]/div[1]/label[1]/input[1]"));
			Radio_button.click();
			Thread.sleep(2000);
			//link
			
			WebElement link = driver.findElement(By.linkText("Add a new credit or debit card"));
			link.click();
			Thread.sleep(4000);
			//card number 
	
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Enter card number
			WebElement card_number = driver.findElement(
			    By.xpath("//input[@class='a-input-text a-form-normal pmts-account-Number']")
			);
			js.executeScript("arguments[0].value='5260998555558888';", card_number);

			// Click Continue button
			WebElement continue_button = driver.findElement(
			    By.xpath("//input[@name='ppw-widgetEvent:AddCreditCardEvent']")
			);
			js.executeScript("arguments[0].click();", continue_button);

			
		} catch (Exception e) {
			test.fail("❌ Test failed due to exception: " + e.getMessage());
			logger.error("Test failed", e);
			Assert.fail("Test failed: " + e.getMessage());
		}
	}
	@AfterSuite
	public void tearDown() {
		// ✅ Step 4: Flush report at the end
		if (driver != null) {
			//driver.quit();
		}
		extent.flush();
		logger.info("📄 Report generated successfully!");
	}
}