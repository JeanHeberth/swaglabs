package br.com.swaglabs.tests.base;

import br.com.swaglabs.config.BrowserConfig;
import br.com.swaglabs.fluxo.Fluxo;
import br.com.swaglabs.page.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected final Fluxo fluxo = new Fluxo();
    protected HomePage acessarPage;

    @BeforeMethod
    public void beforeMethod(){
        BrowserConfig
                .setup();
    }

    @AfterMethod
    public void afterMethod() throws InterruptedException {
        BrowserConfig
                .tearDown();
    }

}
