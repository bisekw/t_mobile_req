package config;

import framework.config.Settings;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigReader {

    public static void PopulateSettings() throws IOException {
        ConfigReader reader = new ConfigReader();
        reader.ReadConfigFile();
    }

    private void ReadConfigFile() throws IOException {
        Properties p = new Properties();
        String environment = System.getProperty("environment");
        String configPath = "src/main/resources/GlobalConfig" + environment + ".properties";
        log.info("Wycztałem konfigurację z pliku: "+configPath);
        InputStream inputStream = new FileInputStream(configPath);
        p.load(inputStream);
        Settings.GRID_URL = p.getProperty("GRID_URL");
        Settings.CHROME_VERSION = p.getProperty("CHROME_VERSION");
        Settings.TESTED_APP_URL = p.getProperty("TESTED_APP_URL");
        Settings.RP_ENDPOINT = p.getProperty("RP_ENDPOINT");
        Settings.RP_LAUNCH = p.getProperty("RP_LAUNCH");
        Settings.RP_PROJECT = p.getProperty("RP_PROJECT");
        Settings.RP_UUID = p.getProperty("RP_UUID");
        Settings.RP_ATTRIBUTES = p.getProperty("RP_ATTRIBUTES");
        Settings.CUCUMBER_RUNNER_TAG = System.getProperty("cucumber.filter.tags");
        Settings.IS_REMOTE_RUN = Boolean.parseBoolean(System.getProperty("IS_REMOTE_RUN"));

    }

}
