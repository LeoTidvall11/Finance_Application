package Utility;

import Models.Transaction;
import Models.TransactionType;



public class EditType  implements IEditTransaction {
    @Override
    public void execute(Transaction transaction,UserInput prompt) {
        TransactionType newType = prompt.promptForType();
        transaction.setType(newType);
        System.out.println("New type: " + newType);
    }
}
