package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;

import java.util.Stack;

/**
 * The abstract Command class provides a template for creating commands that
 * operate based on user input. It initializes various components required to
 * process commands and subcommands.
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
public abstract class Command {
  // user
  protected User user;
  protected InventoryManager inventoryManager;
  protected RecipeManager recipeManager;
  protected Stack<IngredientStorage> history;
  // user input
  protected ValidCommand userInputCommand;
  protected String userInputSubcommand;
  protected String userInputString;
  // utilities
  protected OutputHandler outputHandler;
  protected InputScanner inputScanner;

  /**
   * Constructs a new Command object, initializes various components, and processes the command.
   *
   * @param user The user for whom the command is being created and processed.
   */
  Command(User user) {
    init(user);
    processCommand();
  }

  /**
   * Initializes the command with the given User object, setting up the necessary user input.
   *
   * @param user The User object which contains the necessary information and input for the command.
   */
  public void init(User user) {
    setUser(user);
    setInputScanner(user.getInputScanner());
    setOutputHandler(user.getOutputHandler());
    setInventoryManager(user.getInventoryManager());
    setRecipeManager(user.getRecipeManager());
    history = inventoryManager.getHistory();
    setUserInput(user.getInput());
  }

  private void setInputScanner(InputScanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  private void setOutputHandler(OutputHandler outputHandler) {
    this.outputHandler = outputHandler;
  }

  /**
   * Checks if there is a subcommand present in the user's input.
   *
   * @return {@code true} if a subcommand is present in the user's input; {@code false} otherwise.
   */
  public boolean hasSubcommand() {
    return this.userInputSubcommand != null;
  }

  /**
   * Prompts the user for input by displaying a message if the user input string has not already been set.
   *
   * @param message The message to be printed if the user input string is not set.
   * @return The user's input string.
   */
  protected String getInputString(String message) {
    if (userInputString == null) {
      outputHandler.printInputPrompt(message);
      userInputString = inputScanner.getValidString();
    }
    return userInputString;
  }

  protected void illegalCommand() {
    throw new IllegalCommandCombinationException(userInputCommand.name().toLowerCase(), userInputSubcommand.toLowerCase(), outputHandler);
  }

  /**
   * Handles the processing of a subcommand associated with a user command.
   * Implementations of this method should define the specific behaviors
   * that occur when a subcommand is detected and needs to be processed.
   */
  protected abstract void processSubcommand();

  /**
   * Processes the user command by determining if a subcommand is present. If a subcommand
   * is present, the method delegates the processing to the abstract method processSubcommand.
   * If no subcommand is present, it prints the instruction related to the command.
   */
  private void processCommand() {
    if (hasSubcommand()) {
      processSubcommand();
    } else {
      printInstruction();
    }
  }

  /**
   * Prints the instruction related to the user's input command.
   * This method converts the user input command to a lower case string
   * and delegates the printing of the help message to the output handler.
   */
  private void printInstruction() {
    outputHandler.printHelpMessage(userInputCommand.name().toLowerCase());
  }

  public void setRecipeManager(RecipeManager recipeManager) {
    if (recipeManager != null) {
      this.recipeManager = recipeManager;
    }
  }

  /**
   * Sets the user associated with the command.
   *
   * @param user The User object to be associated with the command.
   */
  private void setUser(User user) {
    this.user = user;
  }

  /**
   * Initializes various components of the command using the provided UserInput object.
   *
   * @param userInput The UserInput object containing the command, subcommand, and input string.
   */
  private void setUserInput(UserInput userInput) {
    setUserValidCommand(userInput);
    setUserInputSubcommand(userInput);
    setUserInputString(userInput);
  }

  /**
   * Sets the user command based on the provided UserInput object.
   *
   * @param userInput The UserInput object containing the main command word.
   */
  private void setUserValidCommand(UserInput userInput) {
    this.userInputCommand = userInput.getCommandWord();
  }

  /**
   * Sets the subcommand string based on the provided UserInput object.
   *
   * @param userInput The UserInput object containing the subcommand information.
   */
  private void setUserInputSubcommand(UserInput userInput) {
    this.userInputSubcommand = userInput.getSubcommand();
  }

  /**
   * Sets the input string for the user's input command based on the provided UserInput object.
   *
   * @param userInput The UserInput object containing the input string to be set.
   */
  private void setUserInputString(UserInput userInput) {
    this.userInputString = userInput.getInputString();
  }

  /**
   * Sets the inventory manager associated with the command.
   *
   * @param inventoryManager The InventoryManager object to be associated with this command.
   */
  private void setInventoryManager(InventoryManager inventoryManager) {
    this.inventoryManager = inventoryManager;
  }

}
