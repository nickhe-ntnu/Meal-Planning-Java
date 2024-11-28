package edu.ntnu.idi.bidata.util.command;

import java.util.Arrays;
import java.util.List;

/**
 * Holds the every valid command.
 * UNKNOWN - non-valid command
 * GO - go to inventory, think of it like a file structure (CD)
 * ADD - add to currently located storage (MKDIR)
 * FIND - search if an ingredient/recipe exist with in the collection.
 * LIST - currently two use cases, list expired, list available recipe
 * HELP - list of all commands.
 *
 * @author Nick Heggø
 * @version 2024-11-28
 */
public enum ValidCommand {
  EXIT,
  ADD,
  REMOVE,
  GO,
  FIND,
  LIST,
  HELP,
  UNKNOWN;

  public static List<String> getCommands() {
    return Arrays.stream(ValidCommand.values())
        .filter(command -> command != HELP)
        .filter(command -> command != EXIT)
        .filter(command -> command != UNKNOWN)
        .map(ValidCommand::name)
        .toList();
  }
}
