package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.user.recipe.RecipeBuilder;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.user.recipe.Step;
import edu.ntnu.idi.bidata.util.command.*;
import edu.ntnu.idi.bidata.util.input.CommandInput;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.List;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-12-05
 */
public class Application {
  private final User user;
  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  /**
   * Initializes a new instance of the Application
   * Sets up the user, input scanner, and output handler.
   */
  public Application() {
    user = userSetup();
    inputScanner = user.getInputScanner();
    outputHandler = user.getOutputHandler();
  }

  /**
   * Starts the application by displaying the help string and entering a loop to process user commands.
   * The loop continues until a command causes the application to exit.
   * It handles any exceptions by printing the error messages.
   */
  public void run() {
    outputHandler.printWelcomeMessage(user.getName());
    outputHandler.printHelpMessage();
    startUpCondition();
    engine();
  }

  /**
   * Allows demo to be more efficient with already exising data.
   * Add all the desired start-up conditions here
   * e.g., create a recipe, create ingredients, add storage locations.
   */
  private void startUpCondition() {
    InventoryManager inventoryManager = user.getInventoryManager();
    inventoryManager.createIngredientStorage("Fridge");
    inventoryManager.setCurrentStorage(inventoryManager.getStorage("fridge"));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Chocolate", 300, ValidUnit.G, 10, 4));
    int amountOfExpiredIngredientToGenerate = 3;
    for (int generatedIngredient = 0; generatedIngredient < amountOfExpiredIngredientToGenerate; generatedIngredient++) {
      inventoryManager.addIngredientToCurrentStorage(new Ingredient("expiredDemo"));
    }
    inventoryManager.createIngredientStorage("Cold Room");
    inventoryManager.setCurrentStorage("CoLd RoOm");
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Potato", 4, ValidUnit.KG, 40, 25));
    inventoryManager.createIngredientStorage("Office Fridge");
    inventoryManager.setCurrentStorage((IngredientStorage) null);

    // Default recipe
    RecipeManager recipeManager = user.getRecipeManager();
    RecipeBuilder builder = new RecipeBuilder();
    builder.setName("Cookie Dough");
    builder.setDescription("Best cookie you will ever taste.");
    List<Measurement> measurements = List.of(
        new Measurement("Flour", 500, ValidUnit.G),
        new Measurement("Chocolate chips", 100, ValidUnit.G),
        new Measurement("Milk", 4, ValidUnit.DL)
    );
    builder.addStep(new Step("Combine everything in a bowl", measurements));
    builder.addStep(new Step("And enjoy!", null));
    recipeManager.addRecipe(builder.getRecipe());
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
        this.user.setCommandInput(inputScanner.fetchCommand());
        running = processUserCommand(user.getCommandInput());
      } catch (IllegalArgumentException | AbortException e) {
        outputHandler.printOutput(e.getMessage());
      } catch (IllegalCommandCombinationException illegalCommandCombinationException) {
        outputHandler.printOutput(illegalCommandCombinationException.getMessage());
        outputHandler.printCommandHelpMessage(user.getCommandInput().getCommand());
      }
    } while (running);
  }

  /**
   * Set up the current
   *
   * @return the newly created user object.
   */
  private User userSetup() {
    User createdUser = new User();
    createdUser.setName("Developer");
    //FIXME replace with: createdUser.setName(inputScanner.getValidString().replaceAll("\\s", ""));
    return createdUser;
  }

  /**
   * Processes the user command and redirects to the appropriate function based on the command word.
   * Catches and handles IllegalArgumentExceptions that may occur during execution.
   *
   * @param commandInput The command entered by the user.
   * @return The running condition of the application. Returning false will cause the application to exit.
   */
  private boolean processUserCommand(CommandInput commandInput) {
    boolean running = true;
    ValidCommand command = commandInput.getCommand();
    switch (command) {
      case UNKNOWN -> outputHandler.printCommandHelpMessage(command);
      case HELP -> new HelpCommand(this.user);
      case LIST -> new ListCommand(this.user);
      case GO -> new GoCommand(this.user);
      case ADD -> new AddCommand(this.user);
      case REMOVE -> new RemoveCommand(this.user);
      case FIND -> new FindCommand(this.user);
      case CLEAR -> outputHandler.clearScreen();
      case EXIT -> running = exitApplication();
      default -> throw new IllegalArgumentException("Unexpected command: " + command);
    }
    return running;
  }

  /**
   * Processes an unknown command by printing a help message specific to the command word.
   *
   * @param commandInput The command entered by the user.
   */
  private void processUnknownCommand(CommandInput commandInput) {
    outputHandler.printCommandHelpMessage(commandInput.getCommand());
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