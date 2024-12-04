package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.input.CommandInput;

import java.util.Stack;

/**
 * The abstract Command class provides a template for creating commands that
 * operate based on user input. It initializes various components required to
 * process commands and subcommands.
 *
 * @author Nick Heggø
 * @version 2024-12-04
 */
public abstract class Command {

  private InventoryManager inventoryManager;
  private RecipeManager recipeManager;
  private Stack<IngredientStorage> history;

  private OutputHandler outputHandler;
  private InputScanner inputScanner;

  private CommandInput commandInput;

  /**
   * Constructs a new Command object, initializes various components, and processes the command.
   *
   * @param user The user for whom the command is being created and processed.
   */
  protected Command(User user) {
    initializeCommand(user);
    processCommand();
  }

  /**
   * Initializes the command with the given User object, setting up the necessary user input.
   *
   * @param user The User object which contains the necessary information and input for the command.
   */
  public void initializeCommand(User user) {
    setInputScanner(user.getInputScanner());
    setOutputHandler(user.getOutputHandler());
    setInventoryManager(user.getInventoryManager());
    setRecipeManager(user.getRecipeManager());
    setHistory(getInventoryManager().getHistory());
    setCommandInput(user.getCommandInput());
  }

  /**
   * Retrieves the current instance of the InputScanner used for input operations.
   *
   * @return the instance of InputScanner associated with the command.
   */
  protected InputScanner getInputScanner() {
    return inputScanner;
  }

  /**
   * Sets the InputScanner object used to handle user input for the command.
   *
   * @param inputScanner The InputScanner object to be associated with this command.
   */
  private void setInputScanner(InputScanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  /**
   * Sets the output handler for handling command outputs.
   *
   * @param outputHandler the OutputHandler object to be associated with the command
   */
  private void setOutputHandler(OutputHandler outputHandler) {
    this.outputHandler = outputHandler;
  }

  /**
   * Retrieves the current instance of the OutputHandler used for handling command outputs.
   *
   * @return the OutputHandler associated with the command.
   */
  protected OutputHandler getOutputHandler() {
    return outputHandler;
  }

  /**
   * Retrieves the current instance of InventoryManager used for managing inventory-related operations.
   *
   * @return the InventoryManager associated with this command.
   */
  protected InventoryManager getInventoryManager() {
    return inventoryManager;
  }

  /**
   * Sets the inventory manager associated with the command.
   *
   * @param inventoryManager The InventoryManager object to be associated with this command.
   */
  private void setInventoryManager(InventoryManager inventoryManager) {
    this.inventoryManager = inventoryManager;
  }

  /**
   * Retrieves the current instance of RecipeManager associated with this command.
   *
   * @return the RecipeManager associated with the command.
   */
  protected RecipeManager getRecipeManager() {
    return recipeManager;
  }

  /**
   * Retrieves the history of ingredient storage changes.
   *
   * @return a stack containing the history of ingredient storage instances.
   */
  protected Stack<IngredientStorage> getHistory() {
    return history;
  }

  /**
   * Sets the history stack of ingredient storages for the command.
   *
   * @param history the stack to be used as the history of ingredient storage changes.
   */
  public void setHistory(Stack<IngredientStorage> history) {
    this.history = history;
  }

  /**
   * Retrieves the subcommand from the command input.
   *
   * @return the subcommand as a string, or null if no subcommand is present.
   */
  protected String getSubcommand() {
    return commandInput.getSubcommand();
  }

  /**
   * Checks if the current command input contains a subcommand.
   *
   * @return true if a subcommand is present, false otherwise.
   */
  public boolean hasSubcommand() {
    return commandInput.hasSubcommand();
  }

  /**
   * Retrieves the command argument from the input.
   *
   * @return the input string representing the user command argument.
   */
  protected String getArgument() {
    return commandInput.getArgument();
  }

  /**
   * Sets the command input argument to a valid string provided by the user if it is currently null.
   *
   * @param message the prompt message to display to the user if the argument is empty.
   */
  protected void setArgumentIfEmpty(String message) {
    if (commandInput.getArgument() == null) {
      outputHandler.printInputPrompt(message);
      commandInput.setArgument(inputScanner.collectValidString());
    }
  }

  /**
   * If command and subcommand don't match anything, it should be called this method.
   *
   * @throws IllegalCommandCombinationException indicating an illegal combination of command and subcommand.
   */
  protected void illegalCommand() {
    throw new IllegalCommandCombinationException(commandInput.getCommand(), commandInput.getSubcommand());
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
      printCommandHelpMessage();
    }
  }

  /**
   * Prints the instruction related to the user's input command.
   * This method converts the user input command to a lower case string
   * and delegates the printing of the help message to the output handler.
   */
  private void printCommandHelpMessage() {
    outputHandler.printCommandHelpMessage(commandInput.getCommand());
  }

  /**
   * Sets the RecipeManager instance associated with this command.
   *
   * @param recipeManager The RecipeManager object to be set. Must not be null.
   */
  public void setRecipeManager(RecipeManager recipeManager) {
    if (recipeManager != null) {
      this.recipeManager = recipeManager;
    }
  }

  /**
   * Sets the command input for the current command.
   *
   * @param commandInput The CommandInput object representing the user's command input details.
   */
  private void setCommandInput(CommandInput commandInput) {
    this.commandInput = commandInput;
  }

}
