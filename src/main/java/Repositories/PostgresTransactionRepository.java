package Repositories;

import Infrastructure.Database;
import Models.Transaction;
import Models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresTransactionRepository implements ITransactionRepository {

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, type, amount, category, description, date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, transaction.getId());
            pstmt.setString(2, transaction.getType().toString());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, transaction.getCategory());
            pstmt.setString(5, transaction.getDescription());
            pstmt.setDate(6, new java.sql.Date(transaction.getDate().getTime()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding transaction: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                String description = rs.getString("description");
                java.util.Date date = new java.util.Date(rs.getDate("date").getTime());

                list.add(new Transaction(id, type, amount, category, description, date));
            }

        } catch (SQLException e) {
            System.out.println("Error with listing the transactions: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void removeTransaction(UUID id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error removing transaction: " + e.getMessage());
        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET type=?, amount=?, category=?, description=?, date=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaction.getType().toString());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getCategory());
            pstmt.setString(4, transaction.getDescription());
            pstmt.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
            pstmt.setObject(6, transaction.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating transaction: " + e.getMessage());
        }
    }
}