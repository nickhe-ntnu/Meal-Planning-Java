package edu.ntnu.idi.bidata.util.command;

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
 * @version 2024-11-03
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
}
