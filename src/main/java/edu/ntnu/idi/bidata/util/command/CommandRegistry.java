package edu.ntnu.idi.bidata.util.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The CommandWord class is used to manage valid command words for the application.
 * It initializes a collection of valid commands and provides methods to check
 * the validity of a command word and to retrieve all the valid commands as a string.
 *
 * @author Nick Heggø
 * @version 2024-11-28
 */
public class CommandRegistry {
  private final Map<String, ValidCommand> commandMap;

  /**
   * Constructs a CommandRegistry object, initializing a map to hold valid commands.
   * The validCommands map is populated with predefined commands using initializeValidCommand.
   */
  public CommandRegistry() {
    commandMap = new HashMap<>();
    initializeValidCommand();
  }

  /**
   * Initializes the command map with all valid commands except UNKNOWN.
   * Adds each valid command to the command map.
   */
  private void initializeValidCommand() {
    Arrays.stream(ValidCommand.values())
        .filter(command -> command != ValidCommand.UNKNOWN)
        .forEach(this::addCommandToMap);
  }

  /**
   * Adds a ValidCommand to the command map using a lowercase key.
   *
   * @param command the ValidCommand to be added to the map
   */
  private void addCommandToMap(ValidCommand command) {
    String key = Utility.createKey(command.name());
    commandMap.put(key, command);
  }

  /**
   * Check for if command is valid
   *
   * @param commandWord input string command
   * @return CommandWord enum, or UNKNOWN if it is not found.
   */
  public ValidCommand getCommandWord(String commandWord) {
    ValidCommand command = commandMap.get(Utility.createKey(commandWord));
    return (command == null) ? ValidCommand.UNKNOWN : command;
  }
}
