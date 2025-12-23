package br.com.swaglabs.config;

import br.com.swaglabs.utils.PropertiesReader;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserConfig {

    private BrowserConfig() {
    }

    private static void configSelenide() {
        Configuration.webdriverLogsEnabled = true;
        Configuration.remoteConnectionTimeout = 120000;
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        Configuration.browserCapabilities = options;
    }

    public static void setup() {
        configSelenide();
        clearBrowserCookies();
        clearBrowserCache();
        open(PropertiesReader.get("base_uri"));
        getWebDriver().manage().window().maximize();
    }

    public static void tearDown() {
        clearBrowserCookies();
        closeWebDriver();

    }
}
