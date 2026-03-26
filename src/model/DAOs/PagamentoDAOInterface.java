package model.DAOs;

import model.entities.Pagamento;

import java.util.List;

public interface PagamentoDAOInterface {
    Pagamento insert(Pagamento pagamento);
    Pagamento findById(int id);
    Pagamento getLastAdded();
    List<Pagamento> getAll();
    boolean isAlreadyAdded(Pagamento pagamento);
}
