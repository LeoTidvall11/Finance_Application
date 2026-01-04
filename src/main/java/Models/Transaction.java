package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private TransactionType type;
    private double amount;
    private final UUID id;
    private String category;
    private String description;
    private LocalDate date;

    public Transaction(TransactionType type, double amount, String category, String description, LocalDate date) {
        this.type = type;

        this.amount = amount;

        this.category = category;

        this.description = description;

        this.id = UUID.randomUUID();

        this.date = date;
    }

    public Transaction(UUID id, TransactionType type, double amount, String category, String description, LocalDate date) {
        this.id = id;

        this.type = type;

        this.amount = amount;

        this.category = category;

        this.description = description;

        this.date = date;
    }

    @Override
    public String toString() {
        String formattedDate = dateFormat.format(date);
        return this.type + "\n  " +
                "Amount:" + this.amount + "\n   " +
                "Category:" + this.category + "\n     " +
                "Description:" + this.description + "\n     " +
                "Date:" + formattedDate + "\n     " +
                "ID:" + this.id;
    }

    public UUID getId() {

        return id;

    }


    public double getAmount() {

        return amount;

    }


    public void setAmount(double amount) {

        this.amount = amount;

    }


    public String getCategory() {

        return category;

    }


    public void setCategory(String category) {
        this.category = category;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public TransactionType getType() {

        return type;

    }


    public void setType(TransactionType type) {

        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}

