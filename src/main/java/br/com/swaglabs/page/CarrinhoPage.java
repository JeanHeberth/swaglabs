package br.com.swaglabs.page;

import br.com.swaglabs.elements.CarrinhoElements;

import static com.codeborne.selenide.Condition.visible;

public class CarrinhoPage extends CarrinhoElements {
    public CheckoutOverviewInformationPage clicarNoBotaoCheckout() {
        botaoCheckout.click();
        return new CheckoutOverviewInformationPage();
    }

    public CarrinhoPage clicarNoBotaoRemove() {
        botaoRemove.click();
        return this;
    }

    public CarrinhoPage validarProdutoNoCarrinho(String nomeDoItemNoCarrinho) {
        produtoPorNome(nomeDoItemNoCarrinho)
                .shouldBe(visible);
        return this;
    }

    public CarrinhoPage validarProdutoRemovidoNoCarrinho(String nomeDoItemParaRemoverDoCarrinho) {
        produtoPorNome(nomeDoItemParaRemoverDoCarrinho)
                .shouldNotBe(visible);
        return this;
    }
}


