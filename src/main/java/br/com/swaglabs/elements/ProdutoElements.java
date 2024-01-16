package br.com.swaglabs.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProdutoElements {

    protected SelenideElement botaoAdicionarAoCarrinho
            = $(By.id("add-to-cart-sauce-labs-backpack"));
    protected SelenideElement botaoRemoverDoCarrinho
            = $(By.id("remove-sauce-labs-backpack"));

    protected SelenideElement iconeCarrinho
            = $x("//*[@class='shopping_cart_link']");
}
