package br.com.swaglabs.tests.ct04;

import br.com.swaglabs.page.*;
import br.com.swaglabs.tests.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FazerCheckoutTest extends BaseTest {

    LoginPage loginPage = new LoginPage();
    CheckoutOverviewInformationPage checkoutOverviewInformationPage = new CheckoutOverviewInformationPage();

    @Test
    public void realizarCheckout() {

        // Realizando login
        var userName = System.getenv("user_name");
        var password = System.getenv("password");
        loginPage.realizarLogin(userName, password)
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
