package model.DAOs;

import model.entities.ItemPedido;
import model.entities.Pedido;

import java.util.List;

public interface PedidoDAOInterface {
    Pedido findById(int id);
    List<Pedido> findByClientId(int id);
    Pedido insert(Pedido pedido);
    Pedido getLastAdded();
    void insertItensPedidos(List<ItemPedido> itens);
}
