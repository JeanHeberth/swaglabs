package br.com.swaglabs.page;

import br.com.swaglabs.elements.CarrinhoElements;

public class CarrinhoPage extends CarrinhoElements {
    public CheckoutOverviewInformationPage clicarNoBotaoCheckout() {
        botaoCheckout.click();
        return new CheckoutOverviewInformationPage();
    }


}


