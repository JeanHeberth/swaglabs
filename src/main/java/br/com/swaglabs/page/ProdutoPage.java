package br.com.swaglabs.page;

import br.com.swaglabs.elements.ProdutoElements;

import static com.codeborne.selenide.Condition.visible;

public class ProdutoPage extends ProdutoElements {

    public ProdutoPage clicarNoBotaoAddToCart() {
        botaoAdicionarAoCarrinho.shouldBe(visible);
        botaoAdicionarAoCarrinho.click();
        return this;
    }

    public ProdutoPage clicarNoBotaoRemove() {
        botaoRemoverDoCarrinho.click();
        return this;
    }

    public CarrinhoPage acessarCarrinho() {
        iconeCarrinho.click();
        return new CarrinhoPage();
    }
}
