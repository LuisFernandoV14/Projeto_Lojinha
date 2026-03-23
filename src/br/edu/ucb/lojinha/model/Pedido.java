package br.edu.ucb.lojinha.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int idPedido;
    private Cliente cliente; 
    private LocalDate dataPedido;
    private List<ItemPedido> itens;
    private double valorTotal;

    public Pedido(int idPedido, Cliente cliente, LocalDate dataPedido) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        
        this.itens = new ArrayList<>(); 
        this.valorTotal = 0.0;
    }

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
        this.valorTotal += item.getSubtotal(); 
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

}