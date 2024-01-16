package br.com.swaglabs.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class HomeElements {

    protected ElementsCollection itensEncontrados
            = $$x("//*[@class='inventory_list']");

    protected SelenideElement itemDesejado
            = $x("//*[@class='inventory_item_name ']");
}
