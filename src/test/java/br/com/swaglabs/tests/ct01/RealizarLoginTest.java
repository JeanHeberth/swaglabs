package br.com.swaglabs.tests.ct01;

import br.com.swaglabs.page.LoginPage;
import br.com.swaglabs.tests.base.BaseTest;
import br.com.swaglabs.utils.PropertiesReader;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class RealizarLoginTest extends BaseTest {
    LoginPage loginPage = new LoginPage();


    @Test
    public void realizarLoginValido() {
        var userName = PropertiesReader.get("username");
        var password = PropertiesReader.get("password");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarUrl());
    }

    @Test
    public void realizarLoginInvalidoComUsernameInvalido() {
        var userName = PropertiesReader.get("usernameIncorreto");
        var password = PropertiesReader.get("password");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
    }

    @Test
    public void realizarLoginInvalidoComPasswordInvalido() {
        var userName = PropertiesReader.get("username");
        var password = PropertiesReader.get("passwordIncorreto");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
    }

    @Test
    public void realizarLoginInvalidoComUsernameInvalidoEPasswordInvalido() {
        var userName = PropertiesReader.get("usernameIncorreto");
        var password = PropertiesReader.get("passwordIncorreto");
        loginPage.realizarLogin(userName, password);
        assertTrue(loginPage.validarMensagemDeErroDeLogin());
        //testes
    }

}
