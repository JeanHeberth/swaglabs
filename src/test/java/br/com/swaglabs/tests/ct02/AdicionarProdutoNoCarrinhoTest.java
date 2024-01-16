package br.com.swaglabs.tests.ct02;

import br.com.swaglabs.page.LoginPage;
import br.com.swaglabs.tests.base.BaseTest;
import org.testng.annotations.Test;

public class AdicionarProdutoNoCarrinhoTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @Test
    public void adicionarProdutoNoCarrinho() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        loginPage.realizarLogin(userName, password)
                .escolherItem("Sauce Labs Backpack")
                .clicarNoBotaoAddToCart();
    }

    @Test
    public void RemoverProdutoNoCarrinho() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        loginPage.realizarLogin(userName, password)
                .escolherItem("Sauce Labs Backpack")
                .clicarNoBotaoAddToCart()
                .clicarNoBotaoRemove();


    }

}
