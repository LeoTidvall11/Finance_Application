package Utility;

import Models.Transaction;

public class EditDescription implements IEditTransaction {
    @Override
    public void execute(Transaction transaction, UserInput prompt) {
        String newDescription = prompt.promptForString("Enter new description: ");
        transaction.setDescription(newDescription);
        System.out.println("New description: " + newDescription);
    }
}
