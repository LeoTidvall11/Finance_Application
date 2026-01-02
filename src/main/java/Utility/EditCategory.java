package Utility;

import Models.Transaction;

public class EditCategory implements IEditTransaction{
    @Override
    public void execute(Transaction transaction, UserInput prompt) {
        String newCategory = prompt.promptForString("Enter new category: ");
        transaction.setCategory(newCategory);
        System.out.println("New category: " + newCategory);
    }
}
