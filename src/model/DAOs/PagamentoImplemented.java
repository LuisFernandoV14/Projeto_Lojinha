package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoImplemented implements PagamentoDAOInterface {

    Connection conn = null;

    public PagamentoImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Pagamento insert(Pagamento pagamento) {
        PreparedStatement ps = null;

        try {
            conn.setAutoCommit(false);

            if (findById(pagamento.getIdPagamento()) == null) {

                ps = conn.prepareStatement("INSERT INTO Pagamento (id_pedido, val_pagamento, dat_pagamento) VALUES (?, ?, ?)");
                ps.setInt(1, pagamento.getPedido().getIdPedido());
                ps.setDouble(2, pagamento.getValorPagamento());
                ps.setDate(3, Date.valueOf(pagamento.getDataPagamento()));

            } else {
                System.out.println("O pagamento do pedido com id: " + pagamento.getPedido().getIdPedido() + " já existe");
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
                        rs.getDouble("val_pagamento"),
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

    @Override
    public Pagamento getLastAdded() {

        String sql = "SELECT * FROM Pagamento ORDER BY id_pagamento DESC LIMIT 1";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getInt("id_pedido"),
                        rs.getDouble("val_pagamento"),
                        rs.getDate("dat_pagamento").toLocalDate()
                );
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }

    public boolean isAlreadyAdded(Pagamento pagamento) {

        String sql = "SELECT 1 FROM Pagamento WHERE id_pedido = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pagamento.getPedido().getIdPedido());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Pagamento> getAll() {

        List<Pagamento> result = new ArrayList<>();

        String sql = "SELECT * FROM Pagamento";

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                result.add(new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getInt("id_pedido"),
                        rs.getDouble("val_pagamento"),
                        rs.getDate("dat_pagamento").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return result;
    }

}
