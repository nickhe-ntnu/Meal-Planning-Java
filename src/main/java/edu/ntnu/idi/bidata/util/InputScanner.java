package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.UserInput;

import java.util.Scanner;
import java.util.SimpleTimeZone;

/**
 * @author Nick Heggø
 * @version 2024-10-31
 */
public class InputScanner {
  private UnitTypes unitTypes;
  private final CommandWord commands;
  private Scanner scanner;

  /**
   * Create an input scanner to read from the terminal window.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    commands = new CommandWord();
    unitTypes = new UnitTypes();
  }

  public String getString() {
    System.out.print("> ");
    String inputLine = scanner.nextLine().trim();
    if (inputLine.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    return inputLine;
  }

  public UserInput getCommand() {
    System.out.print("> ");
    String inputLine = scanner.nextLine();
    String[] tokens = inputLine.split("\\s+"); // Split on whitespace

    String command = tokens.length > 0 ? tokens[0] : null;
    String subcommand = tokens.length > 1 ? tokens[1] : null;
    String inputString = tokens.length > 2 ? tokens[2] : null;

    return new UserInput(commands.getCommandWord(command), subcommand, inputString);
  }

  public String getHelpString() {
    return commands.getCommandString();
  }
}
