package edu.ntnu.idi.bidata.util.command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * @version 2024-11-27
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
    return Stream.of(ValidCommand.values())
        .map(Enum::name)
        .filter(name -> !name.equals("HELP") && !name.equals("EXIT") && !name.equals("UNKNOWN"))
        .map(String::toLowerCase)
        .sorted()
        .collect(Collectors.toList());
  }
}
