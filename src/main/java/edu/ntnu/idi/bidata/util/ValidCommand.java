package edu.ntnu.idi.bidata.util;

/**
 * Holds the every valid command.
 * UNKNOWN - non-valid command
 * GO - go to inventory, think of it like a file structure (CD)
 * ADD - add to currently located storage (MKDIR)
 * FIND - search if an ingredient/recipe exist with in the collection.
 * LIST - currently two use cases, list expired, list available recipe
 * HELP - list of all commands.
 * @version 0.0.1
 * @author Nick Heggø
 */
public enum ValidCommand {
  UNKNOWN, ADD, GO, FIND, LIST, HELP, EXIT
}
