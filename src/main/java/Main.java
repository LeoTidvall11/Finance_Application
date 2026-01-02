
import Commands.*;
import Repositories.CsvTransactionRepository;
import Repositories.ITransactionRepository;

import java.util.Scanner;
public class Main {

     static void main(String[] args) {
        final String FILENAME = "transactions.csv";



        try (Scanner scanner = new Scanner(System.in)) {


            ITransactionRepository repository = new CsvTransactionRepository(FILENAME);

            CommandsHandler commandsHandler = new CommandsHandler(scanner);

            ListTransactionsCommand listTransactionsCommand = new ListTransactionsCommand(repository);
            AddTransactionCommand addTransactionCommand = new AddTransactionCommand(repository, scanner);
            RemoveTransactionCommand removeTransactionCommand = new RemoveTransactionCommand(repository, scanner);
            ShowBalanceCommand showBalanceCommand = new ShowBalanceCommand(repository);
            ListByPeriodCommand listByPeriodCommand = new ListByPeriodCommand(repository, scanner);
            EditTransactionCommand editTransactionCommand = new EditTransactionCommand(repository, scanner);


            commandsHandler.registerCommand(listTransactionsCommand);
            commandsHandler.registerCommand(addTransactionCommand);
            commandsHandler.registerCommand(removeTransactionCommand);
            commandsHandler.registerCommand(showBalanceCommand);
            commandsHandler.registerCommand(listByPeriodCommand);
            commandsHandler.registerCommand(editTransactionCommand);

            commandsHandler.run();
        }


    }

}
