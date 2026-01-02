package Repositories;
import Models.Transaction;
import Models.TransactionType;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class CsvTransactionRepository implements ITransactionRepository {

    private final String filepath;
    private final List<Transaction> transactions;

    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

    public CsvTransactionRepository(String filepath) {
        this.filepath = filepath;
        this.transactions = this.loadFromFile();


    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transaction != null)
            this.transactions.add(transaction);
        this.saveToFile();

    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(this.transactions);
    }


    @Override
    public void removeTransaction(UUID id) {
        boolean removed = this.transactions.removeIf(t -> t.getId().equals(id));
        if (removed) {
            this.saveToFile();
        }
    }
    @Override
    public void updateTransaction(Transaction transaction) {
        if (transaction == null)
            return;

        UUID id = transaction.getId();

        for (int i = 0; i<this.transactions.size(); i++) {
            if (this.transactions.get(i).getId().equals(id)) {
                this.transactions.set(i, transaction);

                this.saveToFile();
                return;
            }
        }
    }

    private void saveToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("type,amount,date,category,description,id\n");
            for (Transaction t : this.transactions) {
                String dateStr = Transaction.dateFormat.format(t.getDate());
                String amountStr = decimalFormatter.format(t.getAmount());

                String line = String.join(",",
                        t.getType().name(),
                        amountStr,
                        dateStr,
                        t.getCategory(),
                        t.getDescription(),
                        t.getId().toString());

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunde inte spara till fil " + filepath);
            System.out.println("Felmeddelande " + e.getMessage());
        }
    }

        private List<Transaction> loadFromFile () {
            List<Transaction> loadedList = new ArrayList<>();
            File file = new File(filepath);

            if (!file.exists()) {
                return loadedList;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line = reader.readLine();
                if (line == null || !line.equals("type,amount,date,category,description,id")) {
                    return loadedList;
                }
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(",");
                    if (parts.length != 6) continue;


                    TransactionType type = TransactionType.valueOf(parts[0]);
                    double amount = Double.parseDouble(parts[1]);
                    String dateStr = parts[2];
                    String category = parts[3];
                    String description = parts[4];
                    UUID id = UUID.fromString(parts[5]);

                    loadedList.add(new Transaction(
                            id,type, amount, category, description, Transaction.dateFormat.parse(dateStr)));

                }

            } catch (IOException | ParseException | IllegalArgumentException e) {
                System.out.println("Kunde inte ladda data från " + filepath);
                System.out.println("Felmeddelande " + e.getMessage());

            }
            return loadedList;
        }


    }

