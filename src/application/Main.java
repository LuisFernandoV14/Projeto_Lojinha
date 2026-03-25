package application;

import controller.PaymentService;
import model.entities.Cliente;
import model.entities.Pedido;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String CPF = null; Cliente Cli = null; List<Pedido> pedidos = null;

        while (CPF == null || pedidos == null || pedidos.isEmpty()) {
            CPF = PaymentService.getInstance().identificarUsuario();
            Cli = PaymentService.getInstance().getCliente(CPF);
            System.out.println("\nBem vindo " + Cli.getNome() + "!");
            pedidos = PaymentService.getInstance().getPedidos(Cli);
        }



        System.out.println(PaymentService.getInstance().identificarPedidoParaPagamento(pedidos).toString());

    }
}
