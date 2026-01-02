package Utility;

import Models.Transaction;

public class EditAmount implements IEditTransaction {
    @Override
    public void execute(Transaction transaction,UserInput prompt) {
        double newAmount = prompt.promptForDouble("Enter new amount: ");
        transaction.setAmount(newAmount);
        System.out.println("New Amount: " + newAmount);
    }

}
