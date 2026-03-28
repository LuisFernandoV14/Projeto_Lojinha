package controller;

import model.entities.Cliente;
import model.entities.Pagamento;
import model.entities.Pedido;
import model.utilities.IdManager;

import java.time.LocalDate;
import java.util.*;

public class PaymentService {

    private static PaymentService instance;
    private static Scanner input = null;

    private PaymentService() {
        System.out.println("Conectando com o sistema de pagamentos...");
        input = new Scanner(System.in);
    }

    public static PaymentService getInstance() {
        if (instance == null) {
            instance = new PaymentService();
            System.out.println("\nSistema iniciado! Pressione ENTER para continuar...");
            input.nextLine();
        }
        return instance;
    }

    public List<Pedido> getPedidos(Cliente cliente) {
        List<Pedido> pedidos = IdManager.getInstance().getPedidosByClientId(cliente.getIdCliente());
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado no CPF: " + cliente.getCpf() +
                    "\nVocê não tem nenhum pagamento pendente." +
                    "\n\nVoltando para o ínicio...");
        }
        return pedidos;
    }

    public Pedido identificarPedidoParaPagamento(List<Pedido> pedidos) {

        if (pedidos == null || pedidos.isEmpty()) { return null;}

        System.out.println("\nPor favor, digite o número do pedido que será feito o pagamento: ");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
        }
        System.out.print("\nR: ");
        String escolha = input.nextLine();

        try {

            boolean correto = false;

            for (Pedido pedido : pedidos) {
                if (Integer.parseInt(escolha) == pedido.getIdPedido()) {
                    correto = true;
                    break;
                }
            }

            if (!correto) throw new IllegalArgumentException("\nPor favor digite o número de um pedido.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

        char option = ' ';

        while (true) {
            System.out.print("\nVocê escolheu o pedido de número " + escolha + " para ser pago. Certo? (Y/N) \nR: ");
            option = input.nextLine().toUpperCase().charAt(0);

            if (option == 'Y') {
                return IdManager.getInstance().getPedidoById(Integer.parseInt(escolha));
            } else if (option == 'N') {
                System.out.println("\nVoltando do começo...");
                return null;
            } else {
                System.out.println("Opção Inválida. Por favor escreva Y (yes) ou N (no)\n");
            }
        }
    }

    public Pagamento realizarPagamento (Pedido pedido) {

        System.out.println("\nItens do pedido: " + pedido.getItens() + "\nPreço total: " + pedido.getValorTotal());

        int ultimoIdAdicionado = IdManager.getInstance().getLastPagamentoAdded().getIdPagamento() + 1;

        Pagamento pagamento = IdManager.getInstance().insertPagamento(new Pagamento(ultimoIdAdicionado, pedido.getIdPedido(), pedido.getValorTotal(), LocalDate.now()));

        if (pagamento == null) {
            System.out.println("O pagamento desse pedido já foi realizado.");
        }

        return pagamento;
    }

    public boolean iniciarServico(Cliente cli) {

        if (cli == null) return false;

        List<Pedido> pedidos;

        System.out.println("\nBem vindo " + cli.getNome() + "!");
        pedidos = PaymentService.getInstance().getPedidos(cli);
        if (pedidos == null || pedidos.isEmpty()) return false;

        Pedido pedido = null;
        Pagamento pagamento = null;
        while (pedido == null || pagamento == null) {
            pedido = PaymentService.getInstance().identificarPedidoParaPagamento(pedidos);
            pagamento = PaymentService.getInstance().realizarPagamento(pedido);
        }

        System.out.println("\nPagamento realizado!");

        System.out.print("\nDeseja continuar com o serviço? \nR: ");
        char continuar = input.nextLine().toUpperCase().charAt(0);

        return switch (continuar) {
            case 'Y' -> false;
            case 'N' -> true;
            default -> false;
        };

    }

    // 111.222.333-44 <- 2 pedidos
    // 222.333.444-55 <- 1 pedido
    // 444.555.666-77 <- nenhum pedido



}
