package br.com.swaglabs.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProdutoElements {

    protected SelenideElement botaoAdicionarAoCarrinho
            = $x("//button[text()=\"Add to cart\"]");
    protected SelenideElement botaoRemoverDoCarrinho
            = $x("//button[text()=\"Remove\"]");

    protected SelenideElement iconeCarrinho
            = $x("//*[@class='shopping_cart_link']");
}
