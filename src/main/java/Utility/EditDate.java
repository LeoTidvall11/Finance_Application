package Utility;


import Models.Transaction;

import java.util.Date;

public class EditDate implements IEditTransaction {
    @Override
    public void execute(Transaction transaction, UserInput prompt) {
        Date newDate = prompt.promptForDate("Enter new date: ");
        transaction.setDate(newDate);
        System.out.println("New date: " + newDate);
    }
}
