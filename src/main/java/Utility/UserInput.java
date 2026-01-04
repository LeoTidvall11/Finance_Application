package Utility;

import Models.Transaction;
import Models.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    private final Scanner scanner;

    private static final Pattern DAYS_AGO_PATTERN = Pattern.compile("(\\d+)\\s+days?\\s+ago");
    private static final Pattern WEEKS_AGO_PATTERN = Pattern.compile("(\\d+)\\s+weeks?\\s+ago");
    private static final Pattern MONTHS_AGO_PATTERN = Pattern.compile("(\\d+)\\s+months?\\s+ago");

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
    };

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
        return promptForString(prompt, 1, 255);
    }

    public String promptForString(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            if (input.length() < minLength) {
                System.out.println("Input too short (minimum " + minLength + " characters).");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println("Input too long (maximum " + maxLength + " characters).");
                continue;
            }

            return input;
        }
    }

    public double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                input = input.replace(',', '.');
                double value = Double.parseDouble(input);

                if (value <= 0) {
                    System.out.println("Amount must be positive.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public LocalDate promptForDate(String prompt) {
        System.out.println(prompt);
        System.out.println("  (Press Enter for today, or: 'yesterday', '3 days ago', 'YYYY-MM-DD')");

        while (true) {
            try {
                System.out.print("> ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.isEmpty()) {
                    return LocalDate.now();
                }

                return parseSmartDate(input);

            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    private LocalDate parseSmartDate(String input) throws DateTimeParseException {
        switch (input) {
            case "today", "t":
                return LocalDate.now();
            case "yesterday", "y":
                return LocalDate.now().minusDays(1);
            case "last week":
                return LocalDate.now().minusWeeks(1);
            case "last month":
                return LocalDate.now().minusMonths(1);
        }

        Matcher daysMatcher = DAYS_AGO_PATTERN.matcher(input);
        if (daysMatcher.matches()) {
            return LocalDate.now().minusDays(Integer.parseInt(daysMatcher.group(1)));
        }

        Matcher weeksMatcher = WEEKS_AGO_PATTERN.matcher(input);
        if (weeksMatcher.matches()) {
            return LocalDate.now().minusWeeks(Integer.parseInt(weeksMatcher.group(1)));
        }

        Matcher monthsMatcher = MONTHS_AGO_PATTERN.matcher(input);
        if (monthsMatcher.matches()) {
            return LocalDate.now().minusMonths(Integer.parseInt(monthsMatcher.group(1)));
        }

        return parseExplicitDate(input);
    }

    private LocalDate parseExplicitDate(String input) throws DateTimeParseException {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Could not parse date", input, 0);
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
            System.out.println("There are no transactions. Start by adding some!");
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