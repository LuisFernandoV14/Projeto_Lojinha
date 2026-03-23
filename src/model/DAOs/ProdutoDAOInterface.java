package model.DAOs;

import model.entities.Produto;

public interface ProdutoDAOInterface {
    Produto findById(int id);
}
