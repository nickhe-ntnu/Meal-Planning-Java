package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * Represents a command to clear the screen of output for a specific user.
 * Inherits from the abstract Command class and provides implementation
 * for executing the command to clear the console output.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class ClearCommand extends Command {
  protected ClearCommand(User user) {
    super(user);
  }

  /**
   * Executes the clear screen command by invoking the output handler
   * to clear the console output.
   */
  @Override
  public void execute() {
    getOutputHandler().clearScreen();
  }
}
