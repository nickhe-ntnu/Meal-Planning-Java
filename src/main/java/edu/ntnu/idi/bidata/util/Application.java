package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.util.command.*;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
public class Application {

  private final User user;
  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  public Application() {
    user = userSetUp();
    inputScanner = user.getInputScanner();
    outputHandler = user.getOutputHandler();
  }

  /**
   * Starts the application by displaying the help string and entering a loop to process user commands.
   * The loop continues until a command causes the application to exit.
   * It handles any exceptions by printing the error messages.
   */
  public void initialize() {
    outputHandler.printWelcomeMessage();
    outputHandler.printHelpMessage();
    startUpCondition();
    engine();
  }

  private void startUpCondition() {
    InventoryManager inventoryManager = user.getInventoryManager();
    inventoryManager.addStorage("Fridge");
    inventoryManager.setCurrentStorage(inventoryManager.getStorage("fridge"));
    inventoryManager.addIngredient(new Ingredient("Chocolate", 300, ValidUnit.G, 10, 4));
    int amountOfExpiredIngredientToGenerate = 3;
    for (int generatedIngredient = 0; generatedIngredient < amountOfExpiredIngredientToGenerate; generatedIngredient++) {
      inventoryManager.addIngredient(new Ingredient("expiredDemo"));
    }
    inventoryManager.addStorage("Cold Room");
    inventoryManager.setCurrentStorage("CoLd RoOm");
    inventoryManager.addIngredient(new Ingredient("Potato", 4, ValidUnit.KG, 40, 25));
    inventoryManager.addStorage("Office Fridge");
    inventoryManager.setCurrentStorage((IngredientStorage) null);
  }

  /**
   * Runs the main loop of the application, continually processing user commands until
   * a termination command is received. The method fetches user input through an input scanner,
   * processes the command, and prints any exceptions encountered.
   */
  private void engine() {
    boolean running = true;
    do {
      try {
        outputHandler.printCommandPrompt();
        this.user.setInput(inputScanner.fetchCommand());
        running = processUserCommand(user.getInput());
      } catch (IllegalArgumentException | IllegalCommandCombinationException e) {
        outputHandler.printOutput(e.getMessage());
      }
    } while (running);
  }

  /**
   * Set up the current
   *
   * @return the newly created user object.
   */
  private User userSetUp() {
    User createdUser = new User();
    //    user.setName(inputScanner.getValidString().replaceAll("\\s", ""));
    createdUser.setName("Developer");
    return createdUser;
  }

  /**
   * Processes the user command and redirects to the appropriate function based on the command word.
   * Catches and handles IllegalArgumentExceptions that may occur during execution.
   *
   * @param userInput The command entered by the user.
   * @return The running condition of the application. Returning false will cause the application to exit.
   */
  private boolean processUserCommand(UserInput userInput) {
    boolean running = true;
    ValidCommand validCommand = userInput.getCommandWord();
    switch (validCommand) {
      case UNKNOWN -> processUnknownCommand(userInput);
      case HELP -> new HelpCommand(this.user);
      case LIST -> new ListCommand(this.user);
      case GO -> new GoCommand(this.user);
      case ADD -> new AddCommand(this.user);
      case REMOVE -> new RemoveCommand(this.user);
      case FIND -> new FindCommand(this.user);
      case EXIT -> running = exitApplication();
      default -> throw new IllegalArgumentException("Unexpected command: " + validCommand);
    }
    return running;
  }

  /**
   * Processes an unknown command by printing a help message specific to the command word.
   *
   * @param userInput The command entered by the user.
   */
  private void processUnknownCommand(UserInput userInput) {
    outputHandler.printHelpMessage(userInput.getCommandWord().name().toLowerCase());
  }

  /**
   * Method to exit the endless loop.
   *
   * @return the running condition of the application will cause the app to exit.
   */
  private boolean exitApplication() {
    outputHandler.printGoodbyeMessage();
    return false;
  }
}