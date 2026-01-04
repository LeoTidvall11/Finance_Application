package Commands;

import Models.Transaction;
import Repositories.ITransactionRepository;
import Utility.UserInput;

import java.time.LocalDate;
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
            LocalDate startDate = prompt.promptForDate("Enter start date: ");
            LocalDate endDate = prompt.promptForDate("Enter end date: ");

            if (startDate.isAfter(endDate)) {
                System.out.println("End date cannot be before start date!");
                return;
            }

            List<Transaction> allTransactions = repository.getAllTransactions();


            List<Transaction> filtered = allTransactions.stream()
                    .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                    .toList();

            System.out.println("\n=== Transactions from " + startDate + " to " + endDate + " ===");

            if (filtered.isEmpty()) {
                System.out.println("No transactions found in this period.");
            } else {
                System.out.println("Found " + filtered.size() + " transaction(s):\n");
                filtered.forEach(t -> {
                    System.out.println(t);
                    System.out.println("─".repeat(50));
                });
            }

        } catch (Exception e) {
            System.out.println("Error listing transactions: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "period";
    }

    @Override
    public String getDescription() {
        return "List transactions in a specific time period";
    }
}
