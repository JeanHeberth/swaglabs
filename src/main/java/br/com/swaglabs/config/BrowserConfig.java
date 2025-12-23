package br.com.swaglabs.config;

import br.com.swaglabs.utils.PropertiesReader;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BrowserConfig {

    private BrowserConfig() {
    }

    private static void configSelenide() {
        Configuration.webdriverLogsEnabled = true;
        Configuration.remoteConnectionTimeout = 120000;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        // 🔥 HEADLESS controlado por ambiente
        boolean headless = Boolean.parseBoolean(
                System.getenv().getOrDefault("HEADLESS", "false")
        );
        Configuration.headless = headless;

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // flags obrigatórias no CI
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = options;
    }


    public static void setup() {
        configSelenide();
        clearBrowserCookies();
        clearBrowserCache();
        open(PropertiesReader.get("base_uri"));
    }

    public static void tearDown() {
        clearBrowserCookies();
        closeWebDriver();

    }
}
