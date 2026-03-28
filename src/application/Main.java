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

            // Garante que o usuário digite uma opção válida
            int option = 0;
            try {
                option = ShoppingService.getInstance().escolherServico(Cli);
                if (option > 3 || option <= 0) throw new IllegalArgumentException();
            } catch (Exception ex) {
                System.out.println("\nPor favor, digite uma opção válida");
                continue;
            }

            // Chama o serviço com base na opção do usuário
            continuar = switch (option) {
                case 1 -> ShoppingService.getInstance().iniciarServico(Cli); // Insere um pedido na tabela PEDIDO
                case 2 -> PaymentService.getInstance().iniciarServico(Cli);  // Insere um pagamento na tabela PAGAMENTO
                case 3 -> true;

                default -> false;
            };
        }

        // Se chegar aqui o cliente pediu pra não continuar
        System.out.println("\nEncerrando programa...");
    }
}
