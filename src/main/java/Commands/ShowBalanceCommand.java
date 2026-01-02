package Commands;

import Models.Transaction;
import Models.TransactionType;
import Repositories.ITransactionRepository;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ShowBalanceCommand implements ICommand {

    private final ITransactionRepository repository;

    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

    public ShowBalanceCommand(ITransactionRepository repository) {
        this.repository = repository;

    }

    @Override
    public void execute() {
        List<Transaction> transactions = repository.getAllTransactions();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME){
                totalIncome += t.getAmount();
            }
            else if (t.getType() == TransactionType.EXPENSE){
                totalExpense += t.getAmount();
            }
        }
        double balance = totalIncome - totalExpense;
        System.out.println("=== Current Balance ===");
        System.out.println("Total income: " + decimalFormatter.format(totalIncome));
        System.out.println("Total expenses:  " + decimalFormatter.format(totalExpense));
        System.out.println("-------------------------");
        System.out.println("Total balance:     " + decimalFormatter.format(balance));
    }
    @Override
    public String getName() {
        return "Balance";
    }
    @Override
    public String getDescription() {
        return "Show total balance + total income and total expense";
    }
}
