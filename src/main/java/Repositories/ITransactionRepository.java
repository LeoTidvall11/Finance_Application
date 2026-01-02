package Repositories;

import Models.Transaction;

import java.util.List;
import java.util.UUID;

public interface ITransactionRepository {

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    void removeTransaction(UUID id);

    void updateTransaction(Transaction transaction);
}
