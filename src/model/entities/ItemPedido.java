package model.entities;

import model.utilities.IdManager;

public class ItemPedido {
    private int idItemPedido;
    private Produto produto;
    private int quantidade;
    private int idPedido;

    public ItemPedido(int idItemPedido, int idProduto, int quantidade,  int idPedido) {
        this.idItemPedido = idItemPedido;
        this.produto = IdManager.getInstance().getProdutoById(idProduto);
        this.quantidade = quantidade;
        this.idPedido = idPedido;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", idPedido=" + idPedido +
                '}';
    }
}
