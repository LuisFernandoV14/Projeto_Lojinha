package controller;

import model.entities.Cliente;
import model.entities.Pedido;
import model.utilities.IdManager;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SimpleTimeZone;

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
        return IdManager.getInstance().getClienteByCPF(CPF);
    }

    public List<Pedido> getPedidos(Cliente cliente) {
        return IdManager.getInstance().getPedidosByClientId(cliente.getIdCliente());
    }

    public void identificarPedidoParaPagamento(List<Pedido> pedidos) {

        System.out.println("\nPor favor, digite o número do pedido que será feito o pagamento: ");
        for (Pedido pedido : pedidos) {
            //System.out.println(pedido.getIdPedido() + " - " + pedido.getItens() + " - " + pedido.getValorTotal() + " - Feito em " + pedido.getDataPedido());
            System.out.println(pedido.toString());
        }

    }


   // 111.222.333-44



}
