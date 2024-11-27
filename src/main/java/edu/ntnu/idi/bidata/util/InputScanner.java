package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.util.command.CommandRegistry;
import edu.ntnu.idi.bidata.util.unit.UnitRegistry;

import java.util.Scanner;

/**
 * The InputScanner class is responsible for reading and interpreting user input from the terminal window.
 * It is designed to parse input into predefined commands, subcommands, and additional input strings.
 *
 * @author Nick Heggø
 * @version 2024-11-08
 */
public class InputScanner {
  private final Scanner scanner;
  private final CommandRegistry commandRegistry;
  private final UnitRegistry unitRegistry;

  /**
   * Default constructor for InputScanner.
   * Initializes the internal scanner, command registry, and unit registry.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    commandRegistry = new CommandRegistry();
    unitRegistry = new UnitRegistry();
  }

  /**
   * Constructs an InputScanner instance.
   * Initializes the internal scanner, command registry, and unit registry.
   *
   * @param scannerSource the Scanner object to be used for input parsing.
   */
  public InputScanner(Scanner scannerSource) {
    scanner = scannerSource;
    commandRegistry = new CommandRegistry();
    unitRegistry = new UnitRegistry();
  }

  /**
   * Fetches and processes user input as a command.
   * Scans the input, tokenizes it, and converts it into a UserInput object.
   *
   * @return a UserInput object representing the parsed command input.
   */
  public UserInput fetchCommand() {
    String scannedLine = scanNextLine();
    assertEmptyInput(scannedLine);
    String[] tokenized = tokenizeInput(scannedLine);
    return createCommandInput(tokenized);
  }

  /**
   * Fetches and processes user input as a unit.
   * The input is scanned, tokenized, and converted into a UserInput object.
   *
   * @return a UserInput object representing the parsed unit input.
   */
  public UserInput fetchUnit() {
    String scannedLine = scanNextLine();
    String[] tokenized = tokenizeInput(scannedLine);
    return createUnitInput(tokenized);
  }

  /**
   * Continuously prompts the user for input until valid input is provided.
   * The input is validated using the getUserInput method, and if invalid,
   * the user is re-prompted until a valid input is entered.
   *
   * @return the valid input string provided by the user.
   */
  public String collectValidString() {
    String result = null;
    boolean valid = false;

    while (!valid) {
      try {
        result = scanNextLine();
        valid = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    return result;
  }

  public float collectValidFloat() {
    float result = 0f;
    boolean validInput = false;

    while (!validInput) {
      try {
        result = getInputFloat();
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    return result;
  }

  /**
   * Reads and returns the next trimmed line from the input.
   *
   * @return the next line of input as a trimmed string.
   * @throws IllegalArgumentException if no input is found.
   */
  public String scanNextLine() {
    assertEmptyLine();
    String inputLine = scanner.nextLine().trim();
    assertEmptyInput(inputLine);
    return inputLine;
  }

  /**
   * Prompts the user for a floating-point number input.
   * If the input is blank or cannot be parsed as a float, an IllegalArgumentException is thrown.
   *
   * @return the floating-point number entered by the user.
   * @throws IllegalArgumentException if the input is blank or cannot be parsed as a float.
   */
  public float getInputFloat() {
    assertEmptyLine();
    return scanner.nextFloat();
  }

  /**
   * Asserts that the input from the scanner is not empty.
   *
   * @throws IllegalArgumentException if no input is found.
   */
  private void assertEmptyLine() {
    if (!scanner.hasNextLine()) {
      throw new IllegalArgumentException("Input cannot be empty.");
    }
  }

  /**
   * Asserts that the input string is not empty.
   *
   * @param input the string input to be validated
   * @throws IllegalArgumentException if the input is empty or consists only of whitespace
   */
  private void assertEmptyInput(String input) {
    if (input.isBlank()) {
      throw new IllegalArgumentException("Input cannot be empty.");
    }
  }

  /**
   * Splits an input line into a maximum of three tokens based on whitespace.
   *
   * @param inputLine the input string to be tokenized.
   * @return an array containing up to three tokens extracted from the input string.
   */
  private String[] tokenizeInput(String inputLine) {
    return inputLine.split("\\s+", 3);
  }

  /**
   * Processes user input tokens and creates a UserInput object based on those tokens.
   *
   * @param tokens an array of strings representing the user's input, split by whitespace.
   *               The first token is treated as the command, the second as the subcommand,
   *               and the third as the full input string.
   * @return a UserInput object containing the parsed command, subcommand, and full input string.
   */
  private UserInput createCommandInput(String[] tokens) {
    String command = (tokens.length > 0) ? tokens[0].toLowerCase() : null;
    String subcommand = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    String inputString = (tokens.length > 2) ? tokens[2] : null;
    return new UserInput(commandRegistry.getCommandWord(command), subcommand, inputString);
  }

  /**
   * Processes user input tokens to create a UserInput object for units.
   *
   * @param tokens an array of strings representing the user's input.
   *               The first token should be the unit amount,
   *               and the second token should be the unit type.
   * @return a UserInput object containing the unit amount and its type.
   */
  private UserInput createUnitInput(String[] tokens) {
    float unitAmount = (tokens.length > 0) ? Float.valueOf(tokens[0]) : -1;
    String unit = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    return new UserInput(unitAmount, unitRegistry.getUnitType(unit));
  }
}
