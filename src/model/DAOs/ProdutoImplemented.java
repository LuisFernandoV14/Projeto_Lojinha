package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Pagamento;
import model.entities.Produto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Produto> getAll() {

        List<Produto> result = new ArrayList<>();

        String sql = "SELECT * FROM Produto";

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int idProduto = rs.getInt("id_produto");
                String nomeProduto = rs.getString("nom_produto");
                double preco = rs.getDouble("preco");

                result.add(new Produto(
                        idProduto,
                        nomeProduto,
                        preco
                ));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return result;
    }



}
