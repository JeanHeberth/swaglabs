package br.com.swaglabs.page;

import br.com.swaglabs.elements.HomeElements;

public class HomePage extends HomeElements {

    public ProdutoPage escolherItem(String nomeDoItem){
        itemDesejado.click();
        return new ProdutoPage();
    }
}
