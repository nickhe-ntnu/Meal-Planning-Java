package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.user.recipe.RecipeBuilder;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.user.recipe.Step;
import edu.ntnu.idi.bidata.util.command.Command;
import edu.ntnu.idi.bidata.util.command.IllegalCommandCombinationException;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.List;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class Application {
  private final User user;
  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;
  private boolean running;

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
    running = true;
    engine();
  }

  public void terminate() {
    outputHandler.printGoodbyeMessage();
    running = false;
  }

  /**
   * Instantiates and executes a user command.
   * Uses the user instance and the current application context
   * to determine and perform the relevant command action.
   */
  private Command getCommand() {
    outputHandler.printCommandPrompt();
    user.setCommandInput(inputScanner.fetchCommand());
    return Command.of(user, this);
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
    List<Measurement> measurements = List.of(
        new Measurement("Flour", 500, ValidUnit.G),
        new Measurement("Chocolate chips", 100, ValidUnit.G),
        new Measurement("Milk", 4, ValidUnit.DL)
    );
    builder.setName("Cookie Dough");
    builder.setDescription("Best cookie you will ever taste.");
    builder.addStep(new Step("Combine everything in a bowl", measurements));
    builder.addStep(new Step("And enjoy!", null));
    recipeManager.addRecipe(builder.getRecipe());
    // second recipe
    builder.setName("Cookie Dough v2");
    builder.setDescription("Best cookie you will ever taste.");
    builder.addStep(new Step("Combine everything in a bowl", measurements));
    builder.addStep(new Step("And enjoy!", null));
    recipeManager.addRecipe(builder.getRecipe());
  }

  /**
   * Continuously processes and executes user commands while the application is running.
   * Catches and handles exceptions related to invalid commands or aborted operations.
   * Uses the output handler to display error messages and command help.
   */
  private void engine() {
    while (running) {
      try {
        Command command = getCommand();
        command.execute();
      } catch (IllegalArgumentException | AbortException e) {
        outputHandler.printOutput(e.getMessage());
      } catch (IllegalCommandCombinationException illegalCommandCombinationException) {
        outputHandler.printOutput(illegalCommandCombinationException.getMessage());
        outputHandler.printCommandHelpMessage(user.getCommandInput().getCommand());
      }
    }
  }

  /**
   * Sets up a new User instance, initializing it with an input name.
   *
   * @return an initialized User object.
   */
  private User userSetup() {
    User createdUser = new User();
    createdUser.setName("Developer");
    //FIXME replace with: createdUser.setName(inputScanner.getValidString().replaceAll("\\s", ""));
    return createdUser;
  }
}