package br.com.swaglabs.tests.ct01;

import br.com.swaglabs.page.HomePage;
import br.com.swaglabs.page.LoginPage;
import br.com.swaglabs.tests.base.BaseTest;
import br.com.swaglabs.utils.PropertiesReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class RealizarLoginTest extends BaseTest {
    LoginPage loginPage = new LoginPage();


    @Test
    public void realizarLoginValido() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarUrl());
    }

    @Test
    public void realizarLoginInvalidoComUsernameInvalido() {
        var userName = System.getenv("user_name_incorreto");
        var password = System.getenv("password");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
    }

    @Test
    public void realizarLoginInvalidoComPasswordInvalido() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password_incorreto");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
    }

    @Test
    public void realizarLoginInvalidoComUsernameInvalidoEPasswordInvalido() {
        var userName = System.getenv("user_name_incorreto");
        var password = System.getenv("password_incorreto");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
    }

}
