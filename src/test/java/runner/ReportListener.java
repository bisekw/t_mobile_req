package runner;



import framework.core.DriverContext;
import framework.core.WebElementHelper;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class ReportListener implements ITestListener {


    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        log.info("On Test Start");
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        if(null!=DriverContext.getRemoteWebDriver())
        {

        }
        log.info("On Test Sucess");
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        log.info("On Test Failure");

    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {

        log.info("On Test Skipped");
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info(" Test Percentage");
    }

    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        log.info("On Test Start");
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
        log.info("On Test Finish");
    }
}
