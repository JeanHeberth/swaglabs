package br.com.swaglabs.page;

import br.com.swaglabs.elements.CarrinhoElements;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

public class CarrinhoPage extends CarrinhoElements {
    public CheckoutOverviewInformationPage clicarNoBotaoCheckout() {
        botaoCheckout.click();
        return new CheckoutOverviewInformationPage();
    }


}


