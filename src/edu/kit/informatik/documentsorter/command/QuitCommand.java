package edu.kit.informatik.documentsorter.command;

import edu.kit.informatik.documentsorter.model.FilingFinesse;

/**
 * This command quits a {@link CommandHandler command handler}.
 *
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public final class QuitCommand implements Command {
    private final CommandHandler commandHandler;
    /**
     * Constructs a new QuitCommand.
     *
     * @param commandHandler the command handler to be quitted
     */
    public QuitCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    @Override
    public CommandResult execute(FilingFinesse model, String[] commandArguments) {
        commandHandler.quit();
        return new CommandResult(CommandResultType.SUCCESS, null);
    }

    @Override
    public int getNumberOfArguments() {
        return 0;
    }
}
