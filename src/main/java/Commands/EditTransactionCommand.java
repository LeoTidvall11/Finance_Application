package Commands;

import Models.Transaction;
import Repositories.ITransactionRepository;
import Utility.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class EditTransactionCommand implements ICommand {

    private final ITransactionRepository repository;
    private final UserInput prompt;
    private final Map<String, IEditTransaction> editActions;

    public EditTransactionCommand(ITransactionRepository repository, Scanner scanner) {
        this.repository = repository;
        this.prompt = new UserInput(scanner);

        this.editActions = new HashMap<>();
        this.editActions.put("1", new EditType());
        this.editActions.put("2", new EditAmount());
        this.editActions.put("3", new EditCategory());
        this.editActions.put("4", new EditDescription());
        this.editActions.put("5", new EditDate());
    }
    @Override
    public void execute() {

        List<Transaction>transactions = repository.getAllTransactions();
        Transaction transaction = prompt.promptForTransactionSelection(transactions, "Alter ");
        if (transaction == null) {
            return;
        }

        System.out.println("Transaction [" + transaction.getId() + "] chosen. ");
        boolean editing = true;

        while (editing) {
            System.out.println(" \n=== Current Transaction ===");
            System.out.println(transaction);
            System.out.println("================================");

            System.out.println("What do you want to change? ");
            System.out.println("1. Type of income (Income or Expense)");
            System.out.println("2. Amount");
            System.out.println("3. Category");
            System.out.println("4. Description");
            System.out.println("5. Date");
            System.out.println("6. Save and exit");


            String menuChoice = prompt.promptForString("> ");

            if (menuChoice.equals("6")) {
                editing = false;
            } else {
                IEditTransaction action = editActions.get(menuChoice);

                if (action != null) {
                    action.execute(transaction, prompt);
                } else {
                    System.out.println("That is not an option, please try again.");
                }


            }

        }
        repository.updateTransaction(transaction);
        System.out.println("Transaction has been updated");
    }
    @Override
    public String getName() {
        return "Edit";
    }
    @Override
    public String getDescription() {
        return "Edit a transaction";
    }




}
