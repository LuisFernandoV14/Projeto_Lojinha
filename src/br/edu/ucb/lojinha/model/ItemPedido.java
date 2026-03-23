package br.edu.ucb.lojinha.model;

public class ItemPedido {
    private int idItemPedido;
    private Produto produto; 
    private int quantidade;

    public ItemPedido(int idItemPedido, Produto produto, int quantidade) {
        this.idItemPedido = idItemPedido;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return this.produto.getPreco() * this.quantidade;
    }

    public int getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(int idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
