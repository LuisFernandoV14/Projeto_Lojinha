package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Pagamento;

import java.sql.*;

public class PagamentoImplemented implements PagamentoDAOInterface {

    Connection conn = null;

    public PagamentoImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Pagamento pagamento) {
        PreparedStatement ps = null;

        try {
            conn.setAutoCommit(false);

            if (findById(pagamento.getIdPagamento()) == null) {

                ps = conn.prepareStatement("INSERT INTO Pagamento (id_pagamento, id_pedido, val_pagamento, dat_pagamento) VALUES (?, ?, ?, ?)");
                ps.setInt(1, pagamento.getIdPagamento());
                ps.setInt(2, pagamento.getPedido().getIdPedido());
                ps.setDouble(3, pagamento.getValorPagamento());


            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Pagamento findById(int id) {

        Statement st = null;
        ResultSet rs = null;
        Pagamento result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Pagamento WHERE id_pagamento = " + id);

            if (rs.next()) {
                result = new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getInt("id_pedido"),
                        rs.getDouble("preco"),
                        rs.getDate("dat_pagamento").toLocalDate()
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

}
