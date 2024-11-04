package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.util.command.ValidCommand;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

/**
 * The UserInput class represents a user command consisting of a primary command,
 * an optional sub-command, and the complete input string.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class UserInput {
  private ValidCommand validCommand;
  private String subcommand;
  private String inputString;

  private ValidUnit validUnit;
  private float unitAmount;

  /**
   * Constructs a Command object with the specified primary command word and an optional sub-command.
   *
   * @param validCommand    The main command word represented by the ValidCommand enum.
   * @param subcommand The optional sub-command as a string.
   */
  public UserInput(ValidCommand validCommand, String subcommand, String inputString) {
    setValidCommand(validCommand);
    setSubcommand(subcommand);
    setInputString(inputString);
  }

  public UserInput(float unitAmount, ValidUnit unitType) {
    setUnitAmount(unitAmount);
    setValidUnit(unitType);
  }

  /**
   * Sets the primary command associated with this UserInput instance.
   *
   * @param validCommand The main command word represented by the ValidCommand enum.
   */
  private void setValidCommand(ValidCommand validCommand) {
    this.validCommand = validCommand;
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

  public ValidUnit getValidUnit() {
    return validUnit;
  }

  /**
   * Retrieves the main command word associated with this UserInput instance.
   *
   * @return The main command word represented by the ValidCommand enum.
   */
  public ValidCommand getCommandWord() {
    return validCommand;
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

  public float getUnitAmount() {
    return unitAmount;
  }

  /**
   * Checks if the command associated with this UserCommand is of type UNKNOWN.
   *
   * @return true if the command is UNKNOWN, false otherwise.
   */
  public boolean isUnknown() {
    return validCommand == ValidCommand.UNKNOWN;
  }

  /**
   * Checks if the UserCommand object contains a subcommand.
   *
   * @return true if a subcommand is present, false otherwise.
   */
  public boolean hasSubcommand() {
    return subcommand != null;
  }

  /**
   * Sets the valid measurement unit associated with this UserInput instance.
   *
   * @param validUnit The measurement unit represented by the ValidUnit enum.
   */
  private void setValidUnit(ValidUnit validUnit) {
    this.validUnit = validUnit;
  }

  private void setUnitAmount(float unitAmount) {
    this.unitAmount = unitAmount;
  }
}
