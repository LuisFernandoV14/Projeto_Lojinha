package br.edu.ucb.lojinha.model;

import java.time.LocalDate;

public class Pagamento {
    private int idPagamento;
    private Pedido pedido; 
    private double valorPagamento;
    private LocalDate dataPagamento; 

    public Pagamento(int idPagamento, Pedido pedido, double valorPagamento) {
        this.idPagamento = idPagamento;
        this.pedido = pedido;
        this.valorPagamento = valorPagamento;
        this.dataPagamento = null; 
    }

    public void aprovarPagamento() {
        this.dataPagamento = LocalDate.now();
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
