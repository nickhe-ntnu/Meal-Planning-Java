package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.util.Application;
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
 * @version 2024-12-07
 */
public abstract class Command {

  private User user;

  /**
   * Constructs a new Command object, initializes various components, and processes the command.
   *
   * @param user The user for whom the command is being created and processed.
   */
  protected Command(User user) {
    setUser(user);
  }

  public static Command of(User user, Application app) {
    ValidCommand command = user.getCommandInput().getCommand();
    return switch (command) {
      case HELP -> new HelpCommand(user);
      case ADD -> new AddCommand(user);
      case FIND -> new FindCommand(user);
      case GO -> new GoCommand(user);
      case LIST -> new ListCommand(user);
      case REMOVE -> new RemoveCommand(user);
      case CLEAR -> new ClearCommand(user);
      case STATS -> new StatsCommand(user);
      case EXIT -> new ExitCommand(user, app);
      case UNKNOWN -> new UnknownCommand(user);
    };
  }

  /**
   * Executes the command action defined in the subclass.
   * This method must be implemented by subclasses to provide
   * specific command execution behavior.
   */
  public abstract void execute();

  /**
   * Processes an unknown command by printing a help message specific to the command word.
   *
   * @param commandInput The command entered by the user.
   */
  protected void processUnknownCommand(CommandInput commandInput) {
    getOutputHandler().printCommandHelpMessage(commandInput.getCommand());
  }
  /**
   * Processes the user command by determining if a subcommand is present. If a subcommand
   * is present, the method delegates the processing to the abstract method processSubcommand.
   * If no subcommand is present, it prints the instruction related to the command.
   */

  /**
   * Checks if the current command input contains a subcommand.
   *
   * @return true if a subcommand is present, false otherwise.
   */
  protected boolean hasSubcommand() {
    return getCommandInput().hasSubcommand();
  }

  /**
   * If command and subcommand don't match anything, it should be called this method.
   *
   * @throws IllegalCommandCombinationException indicating an illegal combination of command and subcommand.
   */
  protected void illegalCommand() {
    throw new IllegalCommandCombinationException(user.getCommandInput().getCommand(), user.getCommandInput().getSubcommand());
  }

  /**
   * Retrieves the current instance of the InputScanner used for input operations.
   *
   * @return the instance of InputScanner associated with the command.
   */
  protected InputScanner getInputScanner() {
    return user.getInputScanner();
  }

  /**
   * Retrieves the current instance of the OutputHandler used for handling command outputs.
   *
   * @return the OutputHandler associated with the command.
   */
  protected OutputHandler getOutputHandler() {
    return user.getOutputHandler();
  }

  /**
   * Retrieves the current instance of InventoryManager used for managing inventory-related operations.
   *
   * @return the InventoryManager associated with this command.
   */
  protected InventoryManager getInventoryManager() {
    return user.getInventoryManager();
  }

  /**
   * Retrieves the current instance of RecipeManager associated with this command.
   *
   * @return the RecipeManager associated with the command.
   */
  protected RecipeManager getRecipeManager() {
    return user.getRecipeManager();
  }

  /**
   * Retrieves the history of ingredient storage changes.
   *
   * @return a stack containing the history of ingredient storage instances.
   */
  protected Stack<IngredientStorage> getHistory() {
    return user.getInventoryManager().getHistory();
  }

  protected CommandInput getCommandInput() {
    return user.getCommandInput();
  }

  protected ValidCommand getCommand() {
    return getCommandInput().getCommand();
  }

  /**
   * Retrieves the subcommand from the command input.
   *
   * @return the subcommand as a string, or null if no subcommand is present.
   */
  protected String getSubcommand() {
    return user.getCommandInput().getSubcommand();
  }

  /**
   * Retrieves the command argument from the input.
   *
   * @return the input string representing the user command argument.
   */
  protected String getArgument() {
    return user.getCommandInput().getArgument();
  }

  private void setUser(User user) {
    this.user = user;
  }

  /**
   * Sets the command input argument to a valid string provided by the user if it is currently null.
   *
   * @param message the prompt message to display to the user if the argument is empty.
   */
  protected void setArgumentIfEmpty(String message) {
    CommandInput commandInput = getCommandInput();
    if (commandInput.getArgument() == null) {
      getOutputHandler().printInputPrompt(message);
      String argument = getInputScanner().collectValidString();
      commandInput.setArgument(argument);
    }
  }
}
