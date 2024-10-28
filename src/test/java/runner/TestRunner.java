package runner;

import io.cucumber.testng.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CucumberOptions(features = {"src/test/java/features"},
        glue = {"steps",
                "classpath:framework.steps"},
     //   tags = "@IOS",
        monochrome = true,
        plugin = {"framework.core.StepsDetails",
                "html:target/cucumber/report.html",
                "json:target/cucumber/report.json",
                "pretty",
                "com.epam.reportportal.cucumber.ScenarioReporter"
            //    "com.epam.reportportal.cucumber.StepReporter"
}

)

@Slf4j
public class TestRunner  {


    private TestNGCucumberRunner testNGCucumberRunner;
    private final InitCrossTestMasterSettings initCrossTestMasterSettings = new InitCrossTestMasterSettings();

    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext iTestContext) {
        try {
            initCrossTestMasterSettings.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        log.info("Przed inicjalizacją testNGCucumberRunner");
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        log.info("Po inicjalizacji testNGCucumberRunner");
    }


    @Test(dataProvider = "features")
    public void runTest(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
      testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }



    @Test(dataProvider = "features")
    public void runTestName(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        String testName = "Rejestracja użytkownika - konto ANONIMOWE";
        List<Pickle> elements = Collections.singletonList(pickleWrapper.getPickle());
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
        for (Pickle scenarioName : elements) {
            if (scenarioName.getName().equals(testName)) {
                testNGCucumberRunner.runScenario(scenarioName);
            }
        }
    }

    @DataProvider
    public Object[] features(ITestContext context) {
        return testNGCucumberRunner.provideScenarios();
    }




    @AfterClass(alwaysRun = true)
    public void afterClass() {
          testNGCucumberRunner.finish();
    }


}
