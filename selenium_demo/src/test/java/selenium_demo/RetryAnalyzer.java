package selenium_demo;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {

        Throwable exception = result.getThrowable();

        if (exception instanceof StaleElementReferenceException ||
            exception instanceof TimeoutException ||
            exception instanceof NoSuchElementException) {

            if (retryCount < maxRetryCount) {
                retryCount++;
                System.out.println("🔁 Retrying due to flaky issue | Attempt: " + retryCount);
                return true;
            }
        }
        return false;
    }
}
