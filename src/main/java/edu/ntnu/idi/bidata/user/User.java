package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.user.recipe.RecipeManager;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;

/**
 * The User class encapsulates information about a user, including their name,
 * personal inventory, cookbook, current directory, and output handler. It provides
 * methods to interact with and manage these attributes.
 *
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class User {

  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  private final InventoryManager inventoryManager;
  private final RecipeManager recipeManager;

  private String name;
  private UserInput input;

  /**
   * Default constructor for the User class.
   * Initializes the input scanner, output handler, inventory manager, and recipe manager.
   * The InputScanner facilitates user interaction.
   * The OutputHandler manages user output.
   * The InventoryManager handles inventory tasks.
   * The RecipeManager manages recipe-related tasks.
   */
  public User() {
    outputHandler = new OutputHandler();
    inputScanner = new InputScanner(outputHandler);
    inventoryManager = new InventoryManager(inputScanner, outputHandler);
    recipeManager = new RecipeManager(inputScanner, outputHandler);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserInput getInput() {
    return this.input;
  }

  public void setInput(UserInput input) {
    this.input = input;
  }

  public InputScanner getInputScanner() {
    return inputScanner;
  }

  public OutputHandler getOutputHandler() {
    return outputHandler;
  }

  public InventoryManager getInventoryManager() {
    return inventoryManager;
  }

  public RecipeManager getRecipeManager() {
    return recipeManager;
  }
}
