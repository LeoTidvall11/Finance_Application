package Commands;

import Models.Transaction;
import Models.TransactionType;
import Repositories.ITransactionRepository;
import Utility.UserInput;

import java.time.LocalDate;
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

            LocalDate date = prompt.promptForDate("Enter Date: ");
            Transaction transaction = new Transaction(type, amount, category, description,date );
            repository.addTransaction(transaction);
            System.out.println("Transaction added! ");
            System.out.println(transaction);

        }catch (RuntimeException e) {
            System.out.println("\n Error adding transaction: " + e.getMessage());
            System.out.println("Please try again");
        }

        catch (Exception e) {
            System.out.println("\n Unexpected error: " + e.getMessage());
        }
    }
    @Override
    public String getName() {
        return "Add";
    }
    @Override
    public String getDescription() {
        return "Adding a new transaction";
    }




}
