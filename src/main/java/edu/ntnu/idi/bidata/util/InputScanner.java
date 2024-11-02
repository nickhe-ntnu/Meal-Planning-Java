package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.UserInput;

import java.util.Scanner;

/**
 * The InputScanner class is responsible for reading and interpreting user input from the terminal window.
 * It is designed to parse input into predefined commands, subcommands, and additional input strings.
 *
 * @author Nick Heggø
 * @version 2024-11-02
 */
public class InputScanner {
  private final Scanner scanner;
  private final CommandWord commands;
  private final UnitTypes unitTypes;

  /**
   * Create an input scanner to read from the terminal window.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    commands = new CommandWord();
    unitTypes = new UnitTypes();
  }

  /**
   * Prompts the user to input a line of text and reads it from the terminal.
   * The input is trimmed of leading and trailing whitespace.
   * If the input is blank, an IllegalArgumentException is thrown.
   *
   * @return the trimmed user input line.
   * @throws IllegalArgumentException if the input is blank.
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
   * Fetches and processes user input from the terminal. The input is trimmed of leading and trailing whitespace,
   * and split into tokens based on whitespace. These tokens are then used to create a UserInput object.
   *
   * @return a UserInput object representing the parsed user input, including command, subcommand, and input string.
   */
  public UserInput fetchUserInput() {
    System.out.print("> ");
    String inputLine = scanner.nextLine().trim();
    String[] tokens = inputLine.split("\\s+", 3); // Split on whitespace
    return setUserInput(tokens);
  }

  /**
   * Processes user input tokens and sets up a UserInput object based on those tokens.
   *
   * @param tokens an array of strings representing the user's input, split by whitespace.
   *               The first token is treated as the command, the second token as the subcommand,
   *               and the third token and beyond as additional input.
   * @return a UserInput object containing the parsed command, subcommand, and input string.
   */
  private UserInput setUserInput(String[] tokens) {
    String command = tokens.length > 0 ? tokens[0] : null;
    String subcommand = tokens.length > 1 ? tokens[1] : null;
    String inputString = tokens.length > 2 ? tokens[2] : null;
    return new UserInput(commands.getCommandWord(command), subcommand, inputString);
  }
}
