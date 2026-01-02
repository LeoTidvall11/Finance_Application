package Commands;

import Models.Transaction;
import Repositories.ITransactionRepository;

import java.util.List;
public class ListTransactionsCommand implements ICommand{

    private final ITransactionRepository repository;

    public ListTransactionsCommand(ITransactionRepository repository) {
        this.repository = repository;
    }


    @Override
    public void execute() {
        System.out.println("--- Transactions ---");

        List<Transaction> transactions = repository.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                System.out.println("[" + (i + 1) + "]");

                System.out.println(t.toString());
                System.out.println("---------------------");
            }

        }
        System.out.println("---This is every transaction---");

    }
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List every transaction";
    }
}
