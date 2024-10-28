package steps;

import framework.core.Base;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import runner.InitCrossTestMasterSettings;

import java.io.IOException;

@Slf4j
public class CucumberHooks extends Base {

    @Before()
    public void init() throws IOException {
        log.info("INIT from Cucumber hooks");
        InitCrossTestMasterSettings init = new InitCrossTestMasterSettings();
        init.init();
    }
}
