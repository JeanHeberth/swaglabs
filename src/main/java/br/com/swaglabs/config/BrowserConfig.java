package br.com.swaglabs.config;

import br.com.swaglabs.utils.PropertiesReader;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.Selectors.byId;

public class BrowserConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserConfig.class);

    private BrowserConfig() {
    }

    private static void configSelenide() {
        Configuration.webdriverLogsEnabled = true;
        // timeout in ms
        String timeout = PropertiesReader.getAny("selenide.timeout", "timeout", "SELENIDE_TIMEOUT");
        if (timeout == null || timeout.isBlank()) timeout = "10000";
        try {
            Configuration.timeout = Long.parseLong(timeout);
        } catch (NumberFormatException e) {
            LOGGER.warn("Timeout inválido '{}', usando 10000ms", timeout);
            Configuration.timeout = 10000;
        }

        Configuration.remoteConnectionTimeout = 120000;

        // browser
        String browser = PropertiesReader.getAny("selenide.browser", "browser", "BROWSER");
        if (browser == null || browser.isBlank()) browser = "chrome";
        Configuration.browser = browser;

        String browserSize = PropertiesReader.getAny("selenide.browserSize", "browserSize", "BROWSER_SIZE");
        if (browserSize == null || browserSize.isBlank()) browserSize = "1920x1080";
        Configuration.browserSize = browserSize;

        // headless control: system / env / yaml / default
        String headlessStr = PropertiesReader.getAny("selenide.headless", "headless", "HEADLESS");
        if (headlessStr == null || headlessStr.isBlank()) headlessStr = "true";
        boolean headless = Boolean.parseBoolean(headlessStr.trim());
        Configuration.headless = headless;

        // baseUrl (centralized)
        String baseUrl = PropertiesReader.getAny("selenide.baseUrl", "baseUrl", "base_uri", "BASE_URL", "BASE_URI");
        if (baseUrl == null || baseUrl.isBlank()) baseUrl = "https://www.saucedemo.com";
        Configuration.baseUrl = baseUrl;
        LOGGER.info("baseUrl resolvida = {}", baseUrl);

        // remote driver (Selenium Grid / Selenoid / BrowserStack)
        String remote = PropertiesReader.getAny("selenide.remote", "remote", "SELENIDE_REMOTE");
        if (remote != null && !remote.isBlank()) {
            Configuration.remote = remote;
            LOGGER.info("Usando WebDriver remoto: {}", remote);
        }

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // accept insecure certs (useful for staging)
        options.setAcceptInsecureCerts(true);

        // flags obrigatórias no CI / headless
        if (headless) {
            // Use the new headless flag when available; fall back to old one if necessary
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        // window size
        if (browserSize.contains("x")) {
            options.addArguments("--window-size=" + browserSize);
        }

        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = options;

        // If not remote, ensure local driver is available (WebDriverManager)
        if (Configuration.remote == null || Configuration.remote.isBlank()) {
            try {
                if ("chrome".equalsIgnoreCase(Configuration.browser)) {
                    // Allow overriding driver version via system/env/yaml
                    String chromedriverVersion = PropertiesReader.getAny("webdriver.chromedriver.version", "WEBDRIVER_CHROMEDRIVER_VERSION");
                    if (chromedriverVersion != null && !chromedriverVersion.isBlank()) {
                        LOGGER.info("Configurando ChromeDriver local versão {} via WebDriverManager", chromedriverVersion);
                        WebDriverManager.chromedriver().driverVersion(chromedriverVersion).setup();
                    } else {
                        LOGGER.info("Configurando ChromeDriver local via WebDriverManager (última)");
                        WebDriverManager.chromedriver().setup();
                    }
                }
                // Add other browsers if needed
            } catch (Exception e) {
                LOGGER.warn("Falha ao configurar WebDriverManager; verifique se os drivers estão disponíveis no PATH", e);
            }
        }

        LOGGER.info("Configuração Selenide -> browser={}, headless={}, browserSize={}, timeout={}, baseUrl={}, remote={}",
                Configuration.browser, Configuration.headless, Configuration.browserSize, Configuration.timeout, Configuration.baseUrl, Configuration.remote);
    }


    public static void setup() {
        // Ensure any previous session is closed to avoid leakage
        try {
            closeWebDriver();
        } catch (Exception e) {
            LOGGER.debug("Nenhum WebDriver anterior para fechar", e);
        }

        configSelenide();

        // Clear state
        try {
            clearBrowserCookies();
            clearBrowserCache();
        } catch (Exception e) {
            LOGGER.warn("Falha ao limpar estado do navegador", e);
        }

        // autoOpen toggle (default true to preserve backward compatibility)
        String autoOpen = PropertiesReader.getAny("selenide.autoOpen", "selenide.autoopen", "AUTO_OPEN");
        boolean shouldOpen = true;
        if (autoOpen != null && !autoOpen.isBlank()) {
            shouldOpen = Boolean.parseBoolean(autoOpen.trim());
        }

        if (shouldOpen) {
            openBaseUrl();
        }
    }

    /**
     * Opens the configured base URL (or "/" if none) and waits for the configured login selector.
     * This method is public so tests can call it explicitly if they prefer explicit control.
     */
    public static void openBaseUrl() {
        String toOpen = Configuration.baseUrl;
        if (toOpen == null || toOpen.isBlank()) {
            LOGGER.warn("Nenhuma base URL configurada (Configuration.baseUrl); abrindo '/' em vez disso");
            open("/");
        } else {
            LOGGER.info("Abrindo base URL: {}", toOpen);
            open(toOpen);
        }

        // Wait for login selector to be visible (safety net). Selector is configurable.
        String loginSelector = PropertiesReader.getAny("login.selector", "LOGIN_SELECTOR", "login_selector", "#user-name");
        try {
            Duration wait = Duration.ofMillis(Configuration.timeout);
            if (loginSelector != null && loginSelector.startsWith("#")) {
                // id-based selector
                $(byId(loginSelector.substring(1))).shouldBe(Condition.visible, wait);
            } else if (loginSelector != null && !loginSelector.isBlank()) {
                $(loginSelector).shouldBe(Condition.visible, wait);
            } else {
                LOGGER.debug("Nenhum seletor de login configurado; pulando espera por visibilidade");
            }
        } catch (Exception e) {
            LOGGER.error("Seletor de login '{}' não visível após {}ms", loginSelector, Configuration.timeout, e);
            // create diagnostics folder
            try {
                Path diagDir = Paths.get("build", "reports", "diagnostics");
                Files.createDirectories(diagDir);
                String ts = String.valueOf(System.currentTimeMillis());
                // screenshot (Selenide will place it under build/reports/tests by default)
                try {
                    String shot = screenshot("diagnostic-" + ts);
                    LOGGER.info("Screenshot salvo: {}", shot);
                } catch (Throwable se) {
                    LOGGER.warn("Falha ao capturar screenshot", se);
                }
                // page source
                try {
                    if (WebDriverRunner.hasWebDriverStarted()) {
                        String pageSource = WebDriverRunner.getWebDriver().getPageSource();
                        if (pageSource != null) {
                            Path out = diagDir.resolve("pagesource-" + ts + ".html");
                            Files.write(out, pageSource.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                            LOGGER.info("Fonte da página salva: {}", out.toAbsolutePath());
                        } else {
                            LOGGER.warn("Fonte da página era nula; pulando salvamento");
                        }
                    } else {
                        LOGGER.warn("WebDriver não iniciado; pulando salvamento do page source");
                    }
                } catch (Throwable pe) {
                    LOGGER.warn("Falha ao salvar fonte da página", pe);
                }
            } catch (IOException ioe) {
                LOGGER.warn("Falha ao criar diretório de diagnóstico", ioe);
            }
            // rethrow to allow test to fail and report
            throw e;
        }
    }

    public static void tearDown() {
        try {
            clearBrowserCookies();
        } catch (Exception e) {
            LOGGER.debug("Nenhum cookie para limpar", e);
        }
        closeWebDriver();

    }
}
