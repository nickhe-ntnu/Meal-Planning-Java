package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.UserInput;

import java.util.Scanner;

/**
 * The InputScanner class is responsible for reading and interpreting user input from the terminal window.
 * It is designed to parse input into predefined commands, subcommands, and additional input strings.
 *
 * @author Nick Heggø
 * @version 2024-11-01
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

  /**
   * Reads a line of input from the user, trims any leading and trailing whitespace,
   * and returns the resulting non-blank string.
   *
   * @return the trimmed non-blank user input string.
   * @throws IllegalArgumentException if the user input is blank.
   */
  public String getUserInput() {
    System.out.print("> ");
    String inputLine = scanner.nextLine().trim();
    if (inputLine.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    return inputLine;
  }

  /**
   * Reads a line of input from the user, parses it into a command, subcommand, and input string,
   * and returns a UserInput object containing these components.
   *
   * @return a UserInput object containing the parsed command, subcommand, and input string from the user's input.
   */
  public UserInput readUserInput() {
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
