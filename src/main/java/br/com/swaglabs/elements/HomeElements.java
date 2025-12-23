package br.com.swaglabs.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class HomeElements {
    protected SelenideElement produtoPorId(String idDoProduto) {
        return $x("//div[text()=\"" + idDoProduto + "\"]");
    }


}
