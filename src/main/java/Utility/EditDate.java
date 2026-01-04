package Utility;


import Models.Transaction;

import java.time.LocalDate;

public class EditDate implements IEditTransaction {
    @Override
    public void execute(Transaction transaction, UserInput prompt) {
        LocalDate newDate = prompt.promptForDate("Enter new date: ");
        transaction.setDate(newDate);
        System.out.println("New date: " + newDate.format(Transaction.dateFormat));    }
}
