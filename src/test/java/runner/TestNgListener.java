package runner;


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestNgListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    /*    if (!iInvokedMethod.isTestMethod()) {
            //Initialize Config
            try {
                ConfigReader.PopulateSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }
}
