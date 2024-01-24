package br.com.swaglabs.tests.ct03;

import br.com.swaglabs.page.*;
import br.com.swaglabs.tests.base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FazerCheckoutTest extends BaseTest {

    HomePage homePage = new HomePage();
    CheckoutOverviewInformationPage checkoutOverviewInformationPage = new CheckoutOverviewInformationPage();

    @BeforeMethod
    public void realizarLogin() {
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        homePage = fluxo
                .acessarSwagLabs(userName, password);
    }

    @Test
    public void realizarCheckout() {
        homePage
                .escolherItem("Sauce Labs Backpack")
                .clicarNoBotaoAddToCart()
                .acessarCarrinho()
                .clicarNoBotaoCheckout()
                .preencherInformacaoCheckout("Luiz Augusto", "Santos de Souza", "72925245");

        // Validando se as informações estão corretas
        List<String> validandoInformacao = Arrays.asList("Luiz Augusto", "Santos de Souza", "72925245");
        assertEquals(validandoInformacao, checkoutOverviewInformationPage.validarTexto("Luiz Augusto", "Santos de Souza", "72925245"));
        checkoutOverviewInformationPage.clicarNoBotaoContinue();
    }
}
