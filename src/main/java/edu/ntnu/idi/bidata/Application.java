package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.user.recipe.RecipeBuilder;
import edu.ntnu.idi.bidata.user.recipe.Step;
import edu.ntnu.idi.bidata.util.AbortException;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.command.Command;
import edu.ntnu.idi.bidata.util.command.IllegalCommandCombinationException;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.List;

/**
 * This is the main skeleton of how the application is running.
 * Both simple and advanced applications is an extension of this class.
 *
 * @author Nick Heggø
 * @version 03-01-2025
 */
public abstract class Application {
  protected final User user;
  protected final InputScanner inputScanner;
  protected final OutputHandler outputHandler;
  protected boolean running;

  protected Application(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null!");
    }
    this.user = user;
    inputScanner = user.getInputScanner();
    outputHandler = user.getOutputHandler();
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
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Flour", 5, ValidUnit.KG, 10, 4));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Chocolate Chips", 0.9f, ValidUnit.KG, 10, 4));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Butter", 0.9f, ValidUnit.KG, 10, 4));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Granulated Sugar", 0.9f, ValidUnit.KG, 10, 4));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Brown Sugar", 0.9f, ValidUnit.KG, 10, 4));

    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Vanilla Extract", 0.9f, ValidUnit.DL, 10, 4));
    inventoryManager.addIngredientToCurrentStorage(new Ingredient("Milk", 0.4f, ValidUnit.L, 10, 4));
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
    RecipeBuilder builder = new RecipeBuilder();
    builder.setName("Cookie Dough");
    builder.setDescription("Perfect for midnight cravings, stress-baking without the baking, " +
        "or impressing your friends with your no-bake skills. Warning: You may not want to share.");

    List<Measurement> step1 = List.of(
        new Measurement("Butter", 115, ValidUnit.G),
        new Measurement("Granulated Sugar", 100, ValidUnit.G),
        new Measurement("Brown Sugar", 50, ValidUnit.G)
    );
    builder.addStep(new Step("In a mixing bowl, beat the softened butter, granulated sugar, and brown sugar until smooth and fluffy.", step1));

    List<Measurement> step2 = List.of(
        new Measurement("Milk", 30, ValidUnit.ML),
        new Measurement("Vanilla Extract", 15, ValidUnit.ML)
    );
    builder.addStep(new Step("Mix in the vanilla extract and milk until well combined.", step2));

    List<Measurement> step3 = List.of(
        new Measurement("Flour", 125, ValidUnit.G)
    );
    builder.addStep(new Step("Gradually add the heat-treated flour and salt, mixing until smooth.", step3));

    List<Measurement> step4 = List.of(
        new Measurement("Chocolate Chips", 90, ValidUnit.G)
    );
    builder.addStep(new Step("Stir in the chocolate chips or other desired mix-ins.", step4));

    builder.addStep(new Step("Enjoy the cookie dough immediately, or refrigerate it in an airtight container for up to 5 days.", null));
    user.getRecipeManager().addRecipe(builder.getRecipe());
  }

  /**
   * Starts the application by displaying the help string
   * and entering a loop to process user commands.
   * The loop continues until a command causes the application to exit.
   * It handles any exceptions by printing the error messages.
   */
  public void run() {
    outputHandler.printWelcomeMessage(user.getName());
    outputHandler.printHelpMessage();
    startUpCondition(); // If wish to start as a blank app, remove this method.
    running = true;
    engine();
  }

  /**
   * Terminates the application by displaying a goodbye message and stopping the execution loop.
   */
  public void terminate() {
    outputHandler.printGoodbyeMessage();
    running = false;
  }

  /**
   * Instantiates and executes a user command.
   * Uses the user instance and the current application context
   * to determine and perform the relevant command action.
   */
  protected abstract Command getCommand(); 

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

}
