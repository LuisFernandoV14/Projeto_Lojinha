package application;

import controller.PaymentService;
import controller.ShoppingService;
import model.entities.Cliente;

public class Main {
    public static void main(String[] args) {

        // "Login" do cliente

        String CPF = null;
        Cliente Cli = null;

        while(CPF == null || Cli == null) {
            CPF = ShoppingService.getInstance().identificarUsuario();
            Cli = ShoppingService.getInstance().getCliente(CPF);
        }

        // ------------------------------------------------

        // Começo do menu
        boolean continuar = false;
        while (!continuar) {

            int option = ShoppingService.getInstance().escolherServico(Cli);

            continuar = switch (option) {
                case 1 -> ShoppingService.getInstance().iniciarServico(Cli); // Insere um pedido na tabela PEDIDO
                case 2 -> PaymentService.getInstance().iniciarServico(Cli);  // Insere um pagamento na tabela PAGAMENTO

                default -> false;
            };

        }

        // Se chegar aqui o cliente pediu pra não continuar
        System.out.println("Encerrando programa...");
    }
}
