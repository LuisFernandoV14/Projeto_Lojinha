package application;

import controller.PaymentService;
import model.entities.Cliente;
import model.entities.Pedido;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String CPF = PaymentService.getInstance().identificarUsuario();
        Cliente Cli = PaymentService.getInstance().getCliente(CPF);
        List<Pedido> pedidos = PaymentService.getInstance().getPedidos(Cli);

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
            System.out.println();
        }

    }
}
