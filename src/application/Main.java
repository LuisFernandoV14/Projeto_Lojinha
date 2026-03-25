package application;

import controller.PaymentService;
import model.entities.Cliente;
import model.entities.Pedido;

import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String CPF = PaymentService.getInstance().identificarUsuario();
        Cliente Cli = PaymentService.getInstance().getCliente(CPF);

        System.out.println("\nBem vindo " + Cli.getNome() + "!");

        List<Pedido> pedidos = PaymentService.getInstance().getPedidos(Cli);

        if (pedidos.isEmpty()) {
            System.out.println("\nVocê não tem nenhum pagamento pendente.");
        } else {
            System.out.println("\nVocê tem " + pedidos.size() + " pagamentos pendentes.");
        }

        PaymentService.getInstance().identificarPedidoParaPagamento(pedidos);

    }
}
