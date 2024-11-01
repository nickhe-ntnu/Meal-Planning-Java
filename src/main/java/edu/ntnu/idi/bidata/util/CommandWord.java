package edu.ntnu.idi.bidata.util;

import java.util.HashMap;

/**
 * The CommandWord class is used to manage valid command words for the application.
 * It initializes a collection of valid commands and provides methods to check
 * the validity of a command word and to retrieve all the valid commands as a string.
 *
 * @author Nick Heggø
 * @version 2024-11-01
 */
public class CommandWord {
  private HashMap<String, ValidCommand> validCommands;

  /**
   * Constructor - initialise the command words.
   */
  public CommandWord() {
    validCommands = new HashMap<>();
    for (ValidCommand command : ValidCommand.values()) {
      if (command != ValidCommand.UNKNOWN) {
        validCommands.put(command.name().toLowerCase(), command);
      }
    }
  }

  /**
   * Check for if command is valid
   * @param commandWord input string command
   * @return CommandWord enum, or UNKNOWN if it is not found.
   */
  public ValidCommand getCommandWord(String commandWord) {
    ValidCommand command = validCommands.get(commandWord);
    if (command != null) {
      return command;
    } else {
      return ValidCommand.UNKNOWN;
    }
  }

  /**
   * Get all valid commands as a string.
   */
  public String getCommandString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Available commands are:\n");
    for (String command : validCommands.keySet()) {
      stringBuilder.append(command).append(" ");
    }
    return stringBuilder.toString();
  }
}
