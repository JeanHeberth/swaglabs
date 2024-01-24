package br.com.swaglabs.page;

import br.com.swaglabs.elements.CheckutOverviewInformationsElements;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutOverviewInformationPage extends CheckutOverviewInformationsElements {
    public void preencherInformacaoCheckout(String firstName, String lastName, String zipPostalCode) {
        campoFirstName.sendKeys(firstName);
        campoLastName.sendKeys(lastName);
        campoZipPostalCode.sendKeys(zipPostalCode);
    }

    public CheckoutOverviewPage clicarNoBotaoContinue() {
        botaoContiue.click();
        return new CheckoutOverviewPage();

    }

    public List<String> validarTexto(String firstName, String lastName, String zipPostalCode) {
        List<String> expectedValues = List.of(firstName, lastName, zipPostalCode);
        for (WebElement element : campoDeInformacaoCheckout) {
            String value = element.getText();
            expectedValues.contains(value);
        }
        return expectedValues;
    }
}
