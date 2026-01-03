
import Commands.*;
import Repositories.ITransactionRepository;
import Repositories.PostgresTransactionRepository;

import java.util.Scanner;
public class Main {

     public static void main(String[] args) {
         Infrastructure.Database.initializeDatabase();



        try (Scanner scanner = new Scanner(System.in)) {


            ITransactionRepository repository = new Repositories.PostgresTransactionRepository();

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
