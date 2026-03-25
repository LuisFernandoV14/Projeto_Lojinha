package model.utilities;

import model.DAOs.ClienteImplemented;
import model.DAOs.PagamentoImplemented;
import model.DAOs.PedidoImplemented;
import model.DAOs.ProdutoImplemented;
import model.entities.*;

import java.util.List;

public class IdManager {

    static ClienteImplemented cliente = null;
    static PagamentoImplemented pagamento = null;
    static PedidoImplemented pedido = null;
    static ProdutoImplemented produto = null;

    static IdManager instance = null;

    private IdManager() {
        cliente = DAOFactory.constructCliente();
        pagamento = DAOFactory.constructPagamento();
        pedido = DAOFactory.constructPedido();
        produto = DAOFactory.constructProduto();
    }

    public static IdManager getInstance() {
        if (instance == null) {
            instance = new IdManager();
        }
        return instance;
    }

    public Cliente getClienteById(int idCliente) {
        return cliente.findById(idCliente);
    }

    public Cliente getClienteByCPF(String CPFCliente) {
        return cliente.findByCPF(CPFCliente);
    }

    public Pagamento getPagamentoById(int idPagamento) {
        return pagamento.findById(idPagamento);
    }

    public Pedido getPedidoById(int idPedido) {
        Pedido result = pedido.findById(idPedido);

        for (ItemPedido item : pedido.getProdutos(idPedido)) {
            result.adicionarItem(item);
        }

        return result;
    }

    public List<Pedido> getPedidosByClientId(int idCliente) {
        List<Pedido> pedidos = pedido.findByClientId(idCliente);

        if (pedidos == null || pedidos.isEmpty()) {
            return pedidos;
        }

        for (Pedido p : pedidos) {

            List<ItemPedido> itens = pedido.getProdutos(p.getIdPedido());

            if (itens != null) {
                for (ItemPedido item : itens) {
                    p.adicionarItem(item);
                }
            }
        }

        return pedidos;
    }

    public Produto getProdutoById(int idProduto) {
        return produto.findById(idProduto);
    }

    public void insertPagamento(Pagamento pag) {
        pagamento.insert(pag);
    }

}
