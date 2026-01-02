package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandsHandler {
    private final Map<String, ICommand> commands;
    private final Scanner scanner;
    private boolean Running;

    private static final String CMD_HELP = "hjälp";
    private static final String CMD_EXIT = "avsluta";

    public CommandsHandler(Scanner scanner) {
        this.scanner = scanner;
        this.Running = true;
        this.commands = new HashMap<>();
    }

    public void registerCommand(ICommand command) {
        this.commands.put(command.getName().toLowerCase(), command);
    }

    public void run() {
        System.out.println("Welcome! \n Type 'help' for available commands.");
        while (Running) {
            System.out.println("\n> ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                continue;
            }
            switch (input) {
                case CMD_HELP:
                    printHelp();
                    break;

                case CMD_EXIT:
                    this.Running = false;
                    System.out.println("=== Saved! now exiting ===");
                    break;

                default:
                    ICommand command = commands.get(input);
                    if (command != null) {
                        command.execute();
                    } else {
                        System.out.println("Unknown command. Type 'help' for available commands.");
                    }
                    break;
            }


        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("-----------------------");
        System.out.println("Help - Shows this menu");
        System.out.println("Exit - Saving and exiting");
        for (ICommand command : commands.values()) {
            System.out.println(" - " + command.getName() + " - " + command.getDescription());
        }
    }
}