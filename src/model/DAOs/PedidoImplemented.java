package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Cliente;
import model.entities.ItemPedido;
import model.entities.Pagamento;
import model.entities.Pedido;

import java.sql.*;
import java.time.LocalDate;
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
            rs = st.executeQuery("SELECT * FROM Pedido WHERE id_cliente = " + id + " AND id_pedido NOT IN (SELECT id_pedido FROM PAGAMENTO)");

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

    @Override
    public void insertItensPedidos(List<ItemPedido> itens) {
        String sql = "INSERT INTO Itens_Pedido (id_pedido, id_produto, num_quantidade) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            for (ItemPedido item : itens) {
                ps.setInt(1, item.getIdPedido());
                ps.setInt(2, item.getProduto().getIdProduto());
                ps.setInt(3, item.getQuantidade());

                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new DbException("Erro ao cancelar transação: " + rollbackEx.getMessage());
            }
            throw new DbException("Erro ao inserir itens: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ItemPedido getLastItemPedidoAdded() {

        String sql = "SELECT * FROM Itens_Pedido ORDER BY id_itens_pedido DESC LIMIT 1";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return new ItemPedido(
                        rs.getInt("id_itens_pedido"),
                        rs.getInt("id_produto"),
                        rs.getInt("num_quantidade"),
                        rs.getInt("id_pedido")
                );
            } else {
                return new ItemPedido(0, 0, 0, 0);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


    @Override
    public Pedido insert(Pedido pedido) {
        PreparedStatement ps = null;

        try {
            conn.setAutoCommit(false);

            if (findById(pedido.getIdPedido()) == null) {

                ps = conn.prepareStatement("INSERT INTO Pedido (id_cliente, dat_pedido) VALUES (?, ?)");
                ps.setInt(1, pedido.getCliente().getIdCliente());
                ps.setDate(2, Date.valueOf(LocalDate.now()));

            } else {
                System.out.println("O pagamento do pedido com id: " + pedido.getIdPedido() + " já existe");
            }

            assert ps != null;
            ps.executeUpdate();

            conn.commit();

        } catch (SQLException e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Aí deu o carai memo");
            }

            throw new DbException(e.getMessage());
        }

        return getLastAdded();

    }

    @Override
    public Pedido getLastAdded() {

        String sql = "SELECT * FROM Pedido ORDER BY id_pedido DESC LIMIT 1";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getDate("dat_pedido").toLocalDate()
                );
            } else {
                return new Pedido(0, 0, null);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


}
