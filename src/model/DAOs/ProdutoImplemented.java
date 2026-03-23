package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Produto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoImplemented implements ProdutoDAOInterface {

    Connection conn = null;

    public ProdutoImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Produto findById(int id) {

        Statement st = null;
        ResultSet rs = null;
        Produto result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Produto WHERE id_produto = " + id);

            if (rs.next()) {
                result = new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nom_produto"),
                        rs.getDouble("preco")
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
