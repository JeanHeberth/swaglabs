package br.com.swaglabs.page;

import br.com.swaglabs.elements.LoginElements;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.exactText;

public class LoginPage extends LoginElements {

    public HomePage realizarLogin(String username, String password) {
        txtUserName.sendKeys(username);
        txtPassword.sendKeys(password);
        btnLogin.click();
        return new HomePage();
    }

    public boolean validarUrl() {
        var url = WebDriverRunner.getWebDriver().getCurrentUrl().endsWith("/inventory.html");
        return url;
    }  public boolean validarUrlParaLoginVazio() {
        var url = WebDriverRunner.getWebDriver().getCurrentUrl().endsWith("https://www.saucedemo.com/");
        return url;
    }

    public boolean validarMensagemDeErroDeLogin() {
        mensagem_erro_login.shouldHave(exactText("Epic sadface: Username and password do not match any user in this service"));
        return true;
    }
}
