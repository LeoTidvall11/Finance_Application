package Commands;

import Models.Transaction;
import Models.TransactionType;
import Repositories.ITransactionRepository;
import Utility.UserInput;

import java.util.Date;
import java.util.Scanner;


public class AddTransactionCommand implements ICommand {
    private final ITransactionRepository repository;
    private final UserInput prompt;

    public AddTransactionCommand(ITransactionRepository repository, Scanner scanner) {
        this.repository = repository;
       this.prompt = new UserInput(scanner);
    }
    @Override
    public void execute() {
        try {
            TransactionType type = prompt.promptForType ();

            double amount = prompt.promptForDouble("Enter Amount: ");

            String category = prompt.promptForString ("Enter Category: ");

            String description = prompt.promptForString ("Enter Description:");

            Date date = prompt.promptForDate ("Enter Date (YYYY/MM/DD): ");

            Transaction transaction = new Transaction(type, amount, category, description,date );
            repository.addTransaction(transaction);
            System.out.println("Transaction added: ");
            System.out.println(transaction);
        }catch (Exception e){
            System.out.println("Error adding transaction!" + e.getMessage());

        }
    }
    @Override
    public String getName() {
        return "Add";
    }
    @Override
    public String getDescription() {
        return "Adding transaction";
    }




}
