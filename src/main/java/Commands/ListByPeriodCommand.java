package Commands;

import Models.Transaction;
import Repositories.ITransactionRepository;
import Utility.UserInput;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ListByPeriodCommand implements ICommand {

    private final ITransactionRepository repository;
    private final UserInput prompt;

    public ListByPeriodCommand(ITransactionRepository repository, Scanner scanner) {
        this.repository = repository;
        this.prompt = new UserInput(scanner);
    }

    @Override
    public void execute() {
        try {
            Date startDate = prompt.promptForDate("Enter start-date (YYYY/MM/DD): ");
            Date endDate = prompt.promptForDate("Enter end-date (YYYY/MM/DD): ");

            if (startDate.after(endDate)) {
                System.out.println("The end-date can't be before the start-date. Please try again.");
                return;
            }

            List<Transaction> allTransactions = repository.getAllTransactions();
            List<Transaction> filteredTransactions = new ArrayList<>();

            for (Transaction transaction : allTransactions) {
                Date transactionDate = transaction.getDate();
                if (!transactionDate.before(startDate) && !transactionDate.after(endDate)) {
                    filteredTransactions.add(transaction);
                }
            }

            String startStr = Transaction.dateFormat.format(startDate);
            String endStr = Transaction.dateFormat.format(endDate);
            System.out.println("=== Transactions between " + startStr + " - " + endStr + "===");

            if (filteredTransactions.isEmpty()) {
                System.out.println("No transaction in that date interval.");
            } else {
                for (Transaction transaction : filteredTransactions) {
                    System.out.println(transaction.toString());
                    System.out.println("=======================");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "period";
    }

    @Override
    public String getDescription() {
        return "Every transaction in a specific time interval";
    }
}