package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * Represents a command that is executed when a user's input cannot be matched
 * to any known command. It provides feedback to the user by printing a help message
 * that corresponds to the unrecognized command input.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class UnknownCommand extends Command {

  public UnknownCommand(User user) {
    super(user);
  }

  /**
   * Executes the unknown command by printing a help message specific to the command.
   * This method is called when the user's input does not match any recognized command.
   */
  @Override
  public void execute() {
    getOutputHandler().printCommandHelpMessage(getCommand());
  }
}
