package br.com.swaglabs.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class CarrinhoElements {

    protected SelenideElement botaoCheckout
            = $(By.id("checkout"));
    protected SelenideElement campoFirstName
            = $x("//*[@data-test='firstName']");
    protected SelenideElement campoLastName
            = $(By.id("last-name"));
    protected SelenideElement campoZipPostalCode
            = $(By.id("postal-code"));
    protected SelenideElement botaoContiue
            = $(By.id("continue"));


    protected SelenideElement produtoPorNome(String nomeProduto) {
        return $x("//div[text()=\"" + nomeProduto + "\"]");
    }

    protected SelenideElement botaoRemove
            = $x("//button[text()=\"Remove\"]");

    protected ElementsCollection campoDeInformacaoCheckout
            = $$x("//*[@class='checkout_info']");
}


