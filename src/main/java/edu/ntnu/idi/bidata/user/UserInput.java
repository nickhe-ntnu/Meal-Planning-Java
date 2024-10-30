package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.util.ValidCommand;

/**
 * Represents a user command consisting of a primary command word and an optional sub-command.
 */
public class UserInput {
  private ValidCommand command;
  private String subcommand;

  /**
   * Constructs a Command object with the specified primary command word and an optional sub-command.
   *
   * @param command    The main command word represented by the ValidCommand enum.
   * @param subcommand The optional sub-command as a string.
   */
  public UserInput(ValidCommand command, String subcommand) {
    this.command = command;
    this.subcommand = subcommand;
  }

  public ValidCommand getCommandWord() {
    return command;
  }

  public String getSubcommand() {
    return subcommand;
  }

  /**
   * Checks if the command associated with this UserCommand is of type UNKNOWN.
   *
   * @return true if the command is UNKNOWN, false otherwise.
   */
  public boolean isUnknown() {
    return command == ValidCommand.UNKNOWN;
  }

  /**
   * Checks if the UserCommand object contains a subcommand.
   *
   * @return true if a subcommand is present, false otherwise.
   */
  public boolean hasSubcommand() {
    return subcommand != null;
  }
}
