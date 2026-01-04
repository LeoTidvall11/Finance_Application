package Commands;
import Models.Transaction;
import Repositories.ITransactionRepository;
import Utility.UserInput;

import java.util.List;
import java.util.Scanner;

public class RemoveTransactionCommand implements ICommand {

    private final ITransactionRepository repository;
    private final UserInput prompt;

    public RemoveTransactionCommand(ITransactionRepository repository, Scanner scanner) {
        this.repository = repository;
        this.prompt = new UserInput(scanner);
    }

    @Override
    public void execute() {
        try {
            List<Transaction> transactions = repository.getAllTransactions();

            Transaction transactionToRemove = prompt.promptForTransactionSelection(transactions, "ta bort ");

            if (transactionToRemove == null) {
                return;
            }
            repository.removeTransaction(transactionToRemove.getId());
            System.out.println("Transaction removed!");
        }
        catch (Exception e) {
            System.out.println("Error removing transaction:  " + e.getMessage() );
        }
    }

    @Override
    public String getName() {
        return "Remove transaction";
    }
    @Override
    public String getDescription() {
        return "Removes a transaction";
    }


    }

