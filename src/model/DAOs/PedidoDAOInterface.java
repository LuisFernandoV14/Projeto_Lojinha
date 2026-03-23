package model.DAOs;

import model.entities.Pedido;

import java.util.List;

public interface PedidoDAOInterface {
    Pedido findById(int id);
    List<Pedido> findByClientId(int id);
}
