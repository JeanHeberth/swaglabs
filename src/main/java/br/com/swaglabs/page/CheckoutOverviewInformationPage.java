package br.com.swaglabs.page;

import br.com.swaglabs.elements.CheckutOverviewInformationsElements;
import java.util.List;
import java.util.Arrays;

public class CheckoutOverviewInformationPage extends CheckutOverviewInformationsElements {
    public void preencherInformacaoCheckout(String firstName, String lastName, String zipPostalCode) {
        campoFirstName.sendKeys(firstName);
        campoLastName.sendKeys(lastName);
        campoZipPostalCode.sendKeys(zipPostalCode);
    }

    public CheckoutOverviewInformationPage clicarNoBotaoContinue() {
        botaoContiue.click();
        return this;

    }

    public List<String> validarTexto(String firstName, String lastName, String zipPostalCode) {
        // Retorna os valores preenchidos nos campos do checkout
        return Arrays.asList(
                campoFirstName.getValue(),
                campoLastName.getValue(),
                campoZipPostalCode.getValue()
        );
    }
}
