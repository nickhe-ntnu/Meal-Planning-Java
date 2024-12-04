package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.util.command.CommandRegistry;
import edu.ntnu.idi.bidata.util.input.CommandInput;
import edu.ntnu.idi.bidata.util.input.UnitInput;
import edu.ntnu.idi.bidata.util.unit.UnitRegistry;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.Scanner;

/**
 * The InputScanner class is responsible for reading and interpreting user input from the terminal window.
 * It is designed to parse input into predefined commands, subcommands, and additional input strings.
 *
 * @author Nick Heggø
 * @version 2024-12-04
 */
public class InputScanner {
  private final OutputHandler outputHandler;
  private final Scanner scanner;

  /**
   * Default constructor for InputScanner.
   * Initializes the internal scanner, command registry, and unit registry.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    outputHandler = new OutputHandler();
  }

  /**
   * Constructs an InputScanner instance using the provided OutputHandler.
   * Initializes the internal scanner, command registry, and unit registry.
   *
   * @param outputHandler the OutputHandler used for managing output display
   */
  public InputScanner(OutputHandler outputHandler) {
    scanner = new Scanner(System.in);
    this.outputHandler = outputHandler;
  }

  /**
   * Constructs an InputScanner instance.
   * Initializes the internal scanner, command registry, and unit registry.
   *
   * @param scannerSource the Scanner object to be used for input parsing.
   */
  public InputScanner(Scanner scannerSource) {
    scanner = scannerSource;
    outputHandler = new OutputHandler();
  }

  /**
   * Fetches and processes user input as a command.
   * Scans the input, tokenizes it, and converts it into a UserInput object.
   *
   * @return a UserInput object representing the parsed command input.
   */
  public CommandInput fetchCommand() {
    String scannedLine = nextLine();
    String[] tokenized = tokenizeInput(scannedLine);
    return createCommandInput(tokenized);
  }

  /**
   * Fetches and processes user input as a unit.
   * The input is scanned, tokenized, and converted into a UserInput object.
   *
   * @return a UserInput object representing the parsed unit input.
   */
  public UnitInput fetchUnit() {
    String scannedLine = nextLine();
    String[] tokenized = tokenizeInput(scannedLine);
    assertUnitInput(tokenized);
    return createUnitInput(tokenized);
  }

  private void assertUnitInput(String[] tokens) {
    if (tokens.length < 2) {
      throw new IllegalArgumentException("Missing inputs.");
    }
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
    boolean validInput = false;

    while (!validInput) {
      try {
        assertEmptyLine();
        result = nextLine();
        validInput = true;
      } catch (IllegalArgumentException e) {
        outputHandler.printInputPrompt(e.getMessage());
      }
    }

    return result;
  }

  public float collectValidFloat() {
    float result = 0.0f;
    boolean validInput = false;

    while (!validInput) {
      try {
        result = nextFloat();
        validInput = true;
      } catch (IllegalArgumentException illegalArgumentException) {
        outputHandler.printInputPrompt(illegalArgumentException.getMessage());
      }
    }

    return result;
  }

  public int collectValidInteger() {
    int result = 0;
    boolean validInput = false;

    while (!validInput) {
      try {
        result = nextInteger();
        validInput = true;
      } catch (IllegalArgumentException illegalArgumentException) {
        outputHandler.printInputPrompt(illegalArgumentException.getMessage());
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
  public String nextLine() {
    assertEmptyLine();
    String inputLine = scanner.nextLine().strip();
    assertEmptyInput(inputLine);
    assertAbort(inputLine);
    return inputLine;
  }

  private void assertAbort(String input) {
    if (Utility.createKey(input).equals("abort")) {
      throw new AbortException();
    }
  }

  /**
   * Prompts the user for a floating-point number input.
   * If the input is blank or cannot be parsed as a float, an IllegalArgumentException is thrown.
   *
   * @return the floating-point number entered by the user.
   * @throws IllegalArgumentException if the input is blank or cannot be parsed as a float.
   */
  public float nextFloat() {
    assertEmptyLine();
    return Float.parseFloat(nextLine());
  }

  public int nextInteger() {
    assertEmptyLine();
    return Integer.parseInt(nextLine());
  }

  /**
   * Asserts that the input from the scanner is not empty.
   *
   * @throws IllegalArgumentException if no input is found.
   */
  private void assertEmptyLine() {
    if (!scanner.hasNextLine()) {
      throw new IllegalArgumentException("There are no lines to scan. (Error: Input Scanner)");
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
      throw new IllegalArgumentException("Input can not be empty. (Error: Input Scanner)");
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
  private CommandInput createCommandInput(String[] tokens) {
    String command = (tokens.length > 0) ? tokens[0].toLowerCase() : null;
    String subcommand = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    String inputString = (tokens.length > 2) ? tokens[2] : null;
    return new CommandInput(CommandRegistry.findCommand(command), subcommand, inputString);
  }

  /**
   * Processes user input tokens to create a UserInput object for units.
   *
   * @param tokens an array of strings representing the user's input.
   *               The first token should be the unit amount,
   *               and the second token should be the unit type.
   * @return a UserInput object containing the unit amount and its type.
   */
  private UnitInput createUnitInput(String[] tokens) {
    float unitAmount = (tokens.length > 0) ? Float.parseFloat(tokens[0]) : -1;
    String unitString = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    ValidUnit unit = UnitRegistry.findUnit(unitString);
    return new UnitInput(unitAmount, unit);
  }
}
