package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.ItemPedido;
import model.entities.Pedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PedidoImplemented implements PedidoDAOInterface {

    Connection conn = null;

    public PedidoImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Pedido findById(int id) {

        Statement st = null;
        ResultSet rs = null;
        Pedido result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Pedido WHERE id_pedido = " + id);

            if (rs.next()) {
                result = new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getDate("dat_pedido").toLocalDate()
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return result;

    }

    @Override
    public List<Pedido> findByClientId(int id) {

        Statement st = null;
        ResultSet rs = null;
        List<Pedido> result = new ArrayList<>();

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Pedido WHERE id_cliente = " + id);

            while (rs.next()) {
                result.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getDate("dat_pedido").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return result;

    }



    public List<ItemPedido> getProdutos(int id) {

        Statement st = null;
        ResultSet rs = null;
        List<ItemPedido> result = new ArrayList<>();
        ItemPedido item;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Itens_Pedido JOIN Produto on Produto.id_produto = Itens_Pedido.id_produto WHERE Itens_Pedido.id_pedido = " + id);

            while (rs.next()) {
                item = new ItemPedido(
                        rs.getInt("id_itens_pedido"),
                        rs.getInt("id_produto"),
                        rs.getInt("num_quantidade"),
                        rs.getInt("id_pedido")
                );
                result.add(item);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return result;

    }

}
