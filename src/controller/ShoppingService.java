package controller;

import model.entities.Cliente;
import model.entities.ItemPedido;
import model.entities.Pedido;
import model.entities.Produto;
import model.utilities.IdManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingService {

    private static ShoppingService instance;
    private static Scanner input;

    private ShoppingService () {
        System.out.println("Conectando com o sistema de compras...");
        input = new Scanner(System.in);
    }

    public static ShoppingService getInstance() {
        if (instance == null) {
            instance = new ShoppingService();
            System.out.println("\nSistema iniciado! Pressione ENTER para continuar...");
            input.nextLine();
        }
        return instance;
    }

    public String identificarUsuario() {

        System.out.print("\nPor favor, digite seu CPF. (Use traços e pontos: 000.000.000-00)\nR: ");
        String CPF = input.nextLine();

        if (CPF.length() != 14) {
            System.out.println("\nCPF inválido. Inclua traços e pontos.");
            return null;
        }

        return CPF;
    }

    public Cliente getCliente(String CPF) {
        Cliente cli = IdManager.getInstance().getClienteByCPF(CPF);
        if (cli == null) {
            System.out.println("CPF não encontrado em nosso sistema.");
        }
        return cli;
    }

    public int escolherServico(Cliente Cli) {
        System.out.println("\nBem vindo " + Cli.getNome() + "! O que deseja fazer?");
        System.out.print("""
                1 - Abrir um pedido
                2 - Pagar um pedido\s
            \s""");
        System.out.print("R: ");

        return Integer.parseInt(input.nextLine());
    }

    public boolean iniciarServico(Cliente Cli) {
        if (Cli == null) return false;

        List<ItemPedido> item = escolherProdutos();
        Pedido p = new Pedido(0, Cli.getIdCliente(), LocalDate.now());

        if (item.isEmpty()) return false;

        p = IdManager.getInstance().insertPedido(p, item);

        System.out.println("\nPedido realizado!");
        System.out.print("\nDeseja continuar com o serviço? \nR: ");
        char continuar = input.nextLine().toUpperCase().charAt(0);

        return switch (continuar) {
            case 'Y' -> false;
            case 'N' -> true;
            default -> false;
        };
    }

    public List<ItemPedido> escolherProdutos() {

        // Pega os últimos IDs do banco de dados
        int idPedidoAtual = IdManager.getInstance().getLastItemPedidoAdded().getIdPedido() + 1;
        int idItemPedidoAtual = IdManager.getInstance().getLastItemPedidoAdded().getIdItemPedido() + 1;

        // Pede input para o usuário
        System.out.println("\nVocê escolheu abrir um pedido...");
        System.out.println("\nEscolha produtos da nossa lista de produtos (digite somente o id do produto): \n");

        // Pega todos os produtos do banco de dados e inicia uma lista vazia
        List<Produto> produtos = IdManager.getInstance().getProdutos();
        int limite = produtos.size();
        List<ItemPedido> escolhidos = new ArrayList<>();

        // Confirmação da escolha do usuário
        while (true) {

            // Carrinho
            if (!escolhidos.isEmpty()) {
                System.out.println("\nCarrinho: ");
                for (ItemPedido item : escolhidos) {
                    System.out.println(item);
                }
                System.out.println(" ");
            }

            // Imprimir cada produto
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
            System.out.print("\nR: ");
            String escolha = input.nextLine();

            char option = ' ';

            if (Integer.parseInt(escolha) > limite || Integer.parseInt(escolha) < 1) {
                System.out.println("Por favor digite um número válido. Maior que 0 e menor que " + (limite+1) + "\n");
                continue;
            }

            // Confirmação
            System.out.print("\nVocê escolheu o produto de número " + escolha + ". Certo? (Y/N) \nR: ");
            option = input.nextLine().toUpperCase().charAt(0);

            if (option == 'Y') {

                // Escolha da quantidade
                System.out.print("\nDigite a quantidade que deseja comprar: ");
                int quantidade = 1;
                try {
                    quantidade = Integer.parseInt(input.nextLine());
                    if (quantidade <= 0) { throw new IllegalArgumentException(); }
                } catch (Exception e) {
                    System.out.println("\nPor favor, digite um número inteiro válido\n");
                    continue;
                }

                // Adicionando o ItemPedido
                escolhidos.add(new ItemPedido(idItemPedidoAtual, Integer.parseInt(escolha), quantidade, idPedidoAtual));
                idPedidoAtual++;

            } else if (option == 'N') {
                System.out.println("\nVoltando do começo...");
                continue;
            } else {
                System.out.println("Opção Inválida. Por favor escreva Y (yes) ou N (no)\n");
                continue;
            }

            System.out.print("\nDeseja continuar escolhendo produtos? (Y/N) \nR: ");
            option = input.nextLine().toUpperCase().charAt(0);

            if (option == 'Y') {
                continue;
            } else if (option == 'N') {
                break;
            } else {
                System.out.println("Opção Inválida. Por favor escreva Y (yes) ou N (no)\n");
                escolhidos.removeLast(); // Para não gerar incongruências, o último produto escolhido sai do "carrinho" quando o usuário escreve baboseira
            }
        }

        return escolhidos;

    }

}
