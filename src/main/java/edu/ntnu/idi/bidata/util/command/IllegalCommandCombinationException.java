package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.util.OutputHandler;

/**
 * Exception thrown to indicate an illegal combination of command and subcommand.
 * This exception is a runtime exception that is thrown when a combination
 * of command and subcommand is not valid within the application's context.
 * When this exception is thrown, a help message related to the invalid command
 * is printed using the provided OutputHandler instance.
 *
 * @author Nick Heggø
 * @version 2024-11-28
 */
public class IllegalCommandCombinationException extends RuntimeException {
  public IllegalCommandCombinationException(String command, String subcommand, OutputHandler outputHandler) {
    super("Invalid command combination: '" + command + "' + '" + subcommand + "'");
  }
}
