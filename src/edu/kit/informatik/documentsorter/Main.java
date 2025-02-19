package edu.kit.informatik.documentsorter;

import edu.kit.informatik.documentsorter.command.CommandHandler;
import edu.kit.informatik.documentsorter.model.FilingFinesse;

/**
 * This class is the entry point of the program.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public final class Main {
    private static final String UTILITY_CLASS_CONSTRUCTOR_MESSAGE = "Utility classes cannot be instantiated";
    private static final String WELCOME_TEXT = "Use one of the following commands: load <path>, run <id>, "
            + "change <id> <file> <number>, quit";
    private Main() {
        throw new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGE);
    }
    /**
     * Starts the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initialise the file system
        FilingFinesse filingFinesse = new FilingFinesse();
        //Start interaction with the user
        CommandHandler commandHandler = new CommandHandler(filingFinesse);
        System.out.println(WELCOME_TEXT);
        commandHandler.handleUserInput();
    }


}
