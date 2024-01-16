package br.com.swaglabs.fluxo;

import br.com.swaglabs.page.HomePage;
import br.com.swaglabs.page.LoginPage;

public class Fluxo {
    LoginPage loginPage = new LoginPage();

    public HomePage acessarSwagLabs(String username, String password) {
        return loginPage
                .realizarLogin(username, password);
    }
}
