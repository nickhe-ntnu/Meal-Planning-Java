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
   * Create an input scanner to read from the terminal window.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    commandRegistry = new CommandRegistry();
    unitRegistry = new UnitRegistry();
  }

  /**
   * Create an input scanner to read from the terminal window.
   */
  public InputScanner(Scanner scannerSource) {
    scanner = scannerSource;
    commandRegistry = new CommandRegistry();
    unitRegistry = new UnitRegistry();
  }

  /**
   * Prompts the user to input a line of text and reads it from the terminal.
   * The input is trimmed of leading and trailing whitespace.
   * If the input is blank, an IllegalArgumentException is thrown.
   *
   * @return the trimmed user input line.
   * @throws IllegalArgumentException if the input is blank.
   */
  public String getInputString() {
    if (!scanner.hasNextLine()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    String inputLine = scanner.nextLine().trim();
    if (inputLine.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    return inputLine;
  }

  /**
   * Continuously prompts the user for input until valid input is provided.
   * The input is validated using the getUserInput method, and if invalid,
   * the user is re-prompted until a valid input is entered.
   *
   * @return the valid input string provided by the user.
   */
  public String getValidString() {
    String inputString = "";
    boolean validInput = false;
    do {
      try {
        inputString = getInputString();
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } while (!validInput);
    return inputString;
  }

  public String getValidString(String message) {

    String inputString = "";
    boolean validInput = false;
    do {
      try {
        inputString = getInputString();
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } while (!validInput);
    return inputString;
  }

  public float getValidFloat() {
    float inputFloat = 0f;
    boolean validInput = false;
    do {
      try {
        inputFloat = getInputFloat();
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        // TODO check if there are error when input is none float.
      }

    } while (!validInput);
    return inputFloat;
  }

  /**
   * Prompts the user for a floating-point number input.
   * If the input is blank or cannot be parsed as a float, an IllegalArgumentException is thrown.
   *
   * @return the floating-point number entered by the user.
   * @throws IllegalArgumentException if the input is blank or cannot be parsed as a float.
   */
  public float getInputFloat() {
    if (!scanner.hasNextLine()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    float inputFloat = Float.parseFloat(scanner.nextLine());
    if (inputFloat == 0.0f) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    return inputFloat;
  }

  /**
   * Fetches and processes user input from the terminal.
   * The input is trimmed of leading and trailing whitespace and split into tokens based on whitespace.
   * These tokens are then used to create a UserInput object.
   *
   * @return a UserInput object representing the parsed user input, including command, subcommand, and input string.
   */
  public UserInput fetchCommand() {
    String[] tokens = scanLineToToken();
    return setCommandInput(tokens);
  }

  /**
   * Continuously prompts the user for input until valid unit input is provided.
   * The input is split into tokens based on whitespace.
   * These tokens are then used to create a UserInput object representing a unit and its amount.
   *
   * @return a UserInput object containing the parsed unit and its amount.
   */
  public UserInput fetchUnit() {
    String[] tokens = new String[3];
    boolean success = false;
    do {
      try {
        tokens = scanLineToToken();
        success = true;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input, please enter in the format:"
            + "{number} {unit}");
      }
    } while (!success);
    return setUnitInput(tokens);
  }

  /**
   * Prompts the user for input, reads the input line, trims any leading and trailing whitespace,
   * and splits the input into tokens based on whitespace.
   *
   * @return an array of strings containing the tokens from the user's input.
   * The array will contain at most three elements: the first token, the second token, and the rest of the input.
   */
  private String[] scanLineToToken() {
    String inputLine = scanner.nextLine().trim();
    return inputLine.split("\\s+", 3);
  }

  /**
   * Processes user input tokens and sets up a UserInput object based on those tokens.
   *
   * @param tokens an array of strings representing the user's input, split by whitespace.
   *               The first token is treated as the command, the second token as the subcommand,
   *               and the third token and beyond as additional input.
   * @return a UserInput object containing the parsed command, subcommand, and input string.
   */
  private UserInput setCommandInput(String[] tokens) {
    String command = (tokens.length > 0) ? tokens[0].toLowerCase() : null;
    String subcommand = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    String inputString = (tokens.length > 2) ? tokens[2].toLowerCase() : null;
    return new UserInput(commandRegistry.getCommandWord(command), subcommand, inputString);
  }

  /**
   * Processes user input tokens and creates a UserInput object based on those tokens.
   *
   * @param tokens an array of strings representing the user's input, split by whitespace.
   *               The first token is treated as the unit amount, and the second token (if present) is treated as the unit.
   * @return a UserInput object containing the parsed unit amount and unit type.
   */
  private UserInput setUnitInput(String[] tokens) {
    float unitAmount = (tokens.length > 0) ? Float.valueOf(tokens[0]) : -1;
    String unit = (tokens.length > 1) ? tokens[1].toLowerCase() : null;
    return new UserInput(unitAmount, unitRegistry.getUnitType(unit));
  }
}
