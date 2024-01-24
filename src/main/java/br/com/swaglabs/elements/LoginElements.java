package br.com.swaglabs.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginElements {

    protected SelenideElement txtUserName
            = $(byId("user-name"));

    protected SelenideElement txtPassword
            = $(byId("password"));
    protected SelenideElement btnLogin
            = $(byId("login-button"));

    protected SelenideElement mensagem_erro_login
            = $x("//*[@data-test='error']");
}
