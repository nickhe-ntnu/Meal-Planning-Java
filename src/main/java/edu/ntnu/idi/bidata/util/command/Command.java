package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.Printable;
import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.AdvancedApplication;
import edu.ntnu.idi.bidata.Application;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.input.CommandInput;

import java.util.List;
import java.util.Stack;

/**
 * The abstract Command class provides a template for creating commands that
 * operate based on user input. It initializes various components required to
 * process commands and subcommands.
 *
 * @author Nick Heggø
 * @version 2024-12-12
 */
public abstract class Command {

  private static User user;

  /**
   * Constructs a new Command object, initializes various components, and processes the command.
   *
   * @param user The user for whom the command is being created and processed.
   */
  protected Command(User user) {
    setUser(user);
  }

  /**
   * Creates and returns a Command instance for the provided user and application context.
   * This method retrieves the command input from the user and processes it into a corresponding command.
   *
   * @param user The user for whom the command is being created and executed.
   * @param app  The application context providing necessary dependencies or information for the command.
   * @return An instance of Command corresponding to the user's input and the application context.
   */
  public static Command of(User user, AdvancedApplication app) {
    ValidCommand command = user.getCommandInput().getCommand();
    return getCommand(command, app, user);
  }

  /**
   * Creates and returns a Command instance based on the provided command word and application context.
   * This method streamlines the creation of commands by invoking a helper method to handle the details.
   *
   * @param commandWord The command word representing the type of command to create.
   * @param app         The application context needed for specific commands, such as EXIT.
   * @return An instance of a subclass of Command corresponding to the provided command word.
   */
  public static Command of(ValidCommand commandWord, Application app) {
    return getCommand(commandWord, app, user);
  }

  /**
   * Creates and returns a specific command instance based on the given command word, application context, and user.
   *
   * @param commandWord The command word representing the type of command to create.
   * @param app         The application context needed for specific commands, such as EXIT.
   * @param user        The user for whom the command is being created and processed.
   * @return An instance of a subclass of Command corresponding to the provided command word.
   */
  private static Command getCommand(ValidCommand commandWord, Application app, User user) {
    return switch (commandWord) {
      case HELP    -> new HelpCommand(user);
      case ADD     -> new AddCommand(user);
      case FIND    -> new FindCommand(user);
      case GO      -> new GoCommand(user);
      case LIST    -> new ListCommand(user);
      case REMOVE  -> new RemoveCommand(user);
      case CLEAR   -> new ClearCommand(user);
      case STATS   -> new StatsCommand(user);
      case EXIT    -> new ExitCommand(user, app);
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
   * @throws IllegalCommandCombinationException indicating an illegal combination
   *                                            of command and subcommand.
   */
  protected void illegalCommand() {
    throw new IllegalCommandCombinationException(user.getCommandInput().getCommand(),
        user.getCommandInput().getSubcommand());
  }

  /**
   * Prints the names of items in a filtered list of Printable objects in numbered format.
   *
   * @param printableList the list of Printable objects to be processed and printed
   */
  protected void printNameOfFiltered(List<?> printableList) {
    List<String> names = printableList.stream()
        .map(printable -> ((Printable) printable).getName())
        .toList();
    getOutputHandler().printList(names, "numbered");
  }

  /**
   * Retrieves the zero-based index as input by the user, after displaying a prompt.
   *
   * @return the index selected by the user, adjusted to be zero-based.
   */
  protected int getIndex(int listSize) {
    getOutputHandler().printInputPrompt("Please select an option:");
    int index = -1;
    while (index < 0 || index > listSize - 1) {
      index = getInputScanner().collectValidInteger() - 1; // index start at 0
    }
    return index;
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
   * Retrieves the current instance of InventoryManager
   * used for managing inventory-related operations.
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

  /**
   * Sets the command input argument to a valid string provided by the user if it is currently null.
   *
   * @param message the prompt message to display to the user if the argument is empty.
   */
  protected void setArgument(String message) {
    getOutputHandler().printInputPrompt(message);
    String argument = getInputScanner().collectValidString();
    getCommandInput().setArgument(argument);
  }

  protected User getUser() {
    return user;
  }

  private void setUser(User user) {
    Command.user = user;
  }

  protected boolean isArgumentEmpty() {
    return getArgument() == null;
  }

}
