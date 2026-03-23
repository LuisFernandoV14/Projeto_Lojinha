package model.utilities;

import DB.DB;
import model.DAOs.ClienteImplemented;
import model.DAOs.PagamentoImplemented;
import model.DAOs.PedidoImplemented;
import model.DAOs.ProdutoImplemented;

public class DAOFactory {
    public static ClienteImplemented constructCliente() {
        return new ClienteImplemented(DB.getConnection());
    }
    public static PedidoImplemented constructPedido() {
        return new PedidoImplemented(DB.getConnection());
    }
    public static PagamentoImplemented constructPagamento() {
        return new PagamentoImplemented(DB.getConnection());
    }
    public static ProdutoImplemented constructProduto() {
        return new ProdutoImplemented(DB.getConnection());
    }
}
