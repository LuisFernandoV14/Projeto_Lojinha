package model.DAOs;

import model.entities.Produto;

import java.util.List;

public interface ProdutoDAOInterface {
    Produto findById(int id);
    List<Produto> getAll();
}
