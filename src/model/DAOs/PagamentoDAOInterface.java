package model.DAOs;

import model.entities.Pagamento;

public interface PagamentoDAOInterface {
    void insert(Pagamento pagamento);
    Pagamento findById(int id);
}
