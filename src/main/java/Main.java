
import Commands.*;
import Repositories.ITransactionRepository;
import Repositories.PostgresTransactionRepository;

import java.util.Scanner;
public class Main {

     public static void main(String[] args) {
         Infrastructure.Database.initializeDatabase();



        try (Scanner scanner = new Scanner(System.in)) {
            ITransactionRepository repository = new PostgresTransactionRepository();
            CommandsHandler commandsHandler = new CommandsHandler(scanner);

            commandsHandler.registerCommand(new ListTransactionsCommand(repository));
            commandsHandler.registerCommand(new AddTransactionCommand(repository, scanner));
            commandsHandler.registerCommand(new RemoveTransactionCommand(repository, scanner));
            commandsHandler.registerCommand(new ShowBalanceCommand(repository));
            commandsHandler.registerCommand(new ListByPeriodCommand(repository, scanner));
            commandsHandler.registerCommand(new EditTransactionCommand(repository, scanner));

            commandsHandler.run();
        }


    }

}
