package model.DAOs;

import model.entities.Cliente;

public interface ClienteDAOInterface {
    public Cliente findById(int id);
    public Cliente findByCPF(String CPF);
}
