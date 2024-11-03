package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.util.command.ValidCommand;

/**
 * The UserInput class represents a user command consisting of a primary command,
 * an optional sub-command, and the complete input string.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class UserInput {
  private ValidCommand command;
  private String subcommand;
  private String inputString;

  /**
   * Constructs a Command object with the specified primary command word and an optional sub-command.
   *
   * @param command    The main command word represented by the ValidCommand enum.
   * @param subcommand The optional sub-command as a string.
   */
  public UserInput(ValidCommand command, String subcommand, String inputString) {
    setCommand(command);
    setSubcommand(subcommand);
    setInputString(inputString);
  }

  /**
   * Sets the primary command associated with this UserInput instance.
   *
   * @param command The main command word represented by the ValidCommand enum.
   */
  private void setCommand(ValidCommand command) {
    this.command = command;
  }

  /**
   * Sets the subcommand associated with this UserInput instance.
   *
   * @param subcommand The optional sub-command as a string.
   */
  private void setSubcommand(String subcommand) {
    this.subcommand = subcommand;
  }

  /**
   * Sets the full input string associated with this user command.
   *
   * @param inputString the input string representing the entire user input.
   */
  private void setInputString(String inputString) {
    this.inputString = inputString;
  }

  /**
   * Retrieves the main command word associated with this UserInput instance.
   *
   * @return The main command word represented by the ValidCommand enum.
   */
  public ValidCommand getCommandWord() {
    return command;
  }

  /**
   * Retrieves the subcommand associated with this UserInput instance.
   *
   * @return the subcommand as a string, or null if no subcommand is present.
   */
  public String getSubcommand() {
    return subcommand;
  }

  /**
   * Retrieves the full input string associated with this user command.
   *
   * @return the input string representing the entire user input.
   */
  public String getInputString() {
    return inputString;
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
