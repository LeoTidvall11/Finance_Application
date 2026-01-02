package Utility;

import Models.Transaction;

public interface IEditTransaction {
    void execute(Transaction transaction, UserInput prompt);
}
