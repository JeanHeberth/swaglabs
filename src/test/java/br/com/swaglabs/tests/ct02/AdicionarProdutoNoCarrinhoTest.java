package br.com.swaglabs.tests.ct02;

import br.com.swaglabs.page.HomePage;
import br.com.swaglabs.tests.base.BaseTest;
import br.com.swaglabs.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdicionarProdutoNoCarrinhoTest extends BaseTest {

    HomePage homePage = new HomePage();

    @BeforeMethod
    public void realizarLogin() {
        var userName = PropertiesReader.get("username");
        var password = PropertiesReader.get("password");
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
    public void removerProdutoNoCarrinho() {
        homePage
                .escolherItem("Test.allTheThings() T-Shirt (Red)")
                .clicarNoBotaoAddToCart()
                .clicarNoBotaoRemove();
    }

}
