package br.com.swaglabs.config;

import br.com.swaglabs.utils.PropertiesReader;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserConfig {
    private BrowserConfig() {
    }

    private static void configSelenide() {
        Configuration.webdriverLogsEnabled = true;
        Configuration.remoteConnectionTimeout = 120000;
        Configuration.remoteConnectionTimeout = 120000;
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
