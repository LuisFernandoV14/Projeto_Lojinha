package model.DAOs;

import DB.DB;
import DB.DbException;
import model.entities.Cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteImplemented implements ClienteDAOInterface {

    Connection conn = null;

    public ClienteImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Cliente findById(int id) {

        Statement st = null;
        ResultSet rs = null;
        Cliente result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Cliente WHERE id_cliente = " + id);

            if (rs.next()) {
                result = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nom_cliente"),
                        rs.getString("CPF")
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
    public Cliente findByCPF(String CPF) {

        Statement st = null;
        ResultSet rs = null;
        Cliente result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Cliente WHERE CPF = '" + CPF + "'");

            if (rs.next()) {
                result = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nom_cliente"),
                        rs.getString("CPF")
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
