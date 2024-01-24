package br.com.swaglabs.tests.ct02;

import br.com.swaglabs.page.HomePage;
import br.com.swaglabs.page.LoginPage;
import br.com.swaglabs.tests.base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdicionarProdutoNoCarrinhoTest extends BaseTest {

    HomePage homePage = new HomePage();

    @BeforeMethod
    public void realizarLogin() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        homePage = fluxo
                .acessarSwagLabs(userName, password);
    }

    @Test
    public void adicionarProdutoNoCarrinho() {
        homePage
                .escolherItem("Sauce Labs Backpack")
                .clicarNoBotaoAddToCart();
    }

    @Test
    public void RemoverProdutoNoCarrinho() {
        homePage
                .escolherItem("Sauce Labs Backpack")
                .clicarNoBotaoAddToCart()
                .clicarNoBotaoRemove();


    }

}
