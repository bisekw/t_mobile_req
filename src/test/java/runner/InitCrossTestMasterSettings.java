package runner;


import framework.config.Settings;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class InitCrossTestMasterSettings {
    public void init() throws IOException {
        log.info("Init CrossTestMaster Settings");
        config.ConfigReader.PopulateSettings();
        System.setProperty("RP_ENDPOINT", Settings.RP_ENDPOINT);
        System.setProperty("RP_UUID", Settings.RP_UUID);
        System.setProperty("RP_LAUNCH", Settings.RP_LAUNCH);
        System.setProperty("RP_PROJECT", Settings.RP_PROJECT);
        Settings.RP_ATTRIBUTES = framework.config.Settings.RP_ATTRIBUTES + "; tag:" + framework.config.Settings.CUCUMBER_RUNNER_TAG;
        System.setProperty("RP_ATTRIBUTES", Settings.RP_ATTRIBUTES);
    }
}
