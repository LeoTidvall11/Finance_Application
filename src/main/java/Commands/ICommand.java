package Commands;

public interface ICommand {
    void execute();

    String getName();

    String getDescription();
}
