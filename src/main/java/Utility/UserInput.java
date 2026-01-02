package Utility;

import Models.Transaction;
import Models.TransactionType;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInput {
    private final Scanner scanner;

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public TransactionType promptForType() {
        while (true) {
            System.out.print("Enter type (1: INCOME, 2: EXPENSE): ");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                return TransactionType.INCOME;
            } else if (input.equals("2")) {
                return TransactionType.EXPENSE;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                input = input.replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public Date promptForDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Transaction.dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid input. Use YYYY/MM/DD");
            }
        }
    }

    public int promptForInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Invalid input. It needs to be between " + min + " & " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    public Transaction promptForTransactionSelection(List<Transaction> transactions, String title) {
        if (transactions.isEmpty()) {
            System.out.println("There are no transaction. Start by adding some transactions!");
            return null;
        }

        System.out.println("=== Choose transaction " + title + " ===");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + transactions.get(i).getCategory() + " (" + transactions.get(i).getAmount() + ")");
        }
        System.out.println("=========================================");

        int choice = this.promptForInt("Enter number (Or 0 to cancel): ", 0, transactions.size());

        if (choice == 0) {
            System.out.println("Cancelling");
            return null;
        }

        return transactions.get(choice - 1);
    }
}