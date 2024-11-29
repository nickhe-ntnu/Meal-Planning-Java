package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;

/**
 * The AddCommand class extends the Command class and is responsible for handling
 * the "add" command by processing subcommands related to adding new items
 * such as locations and storage entries for a given user.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class AddCommand extends Command {

  /**
   * Constructs a new AddCommand object for the given user. This constructor
   * initializes the necessary components and processes the command related
   * to adding new entries.
   *
   * @param user The user for whom the add command is being created and processed.
   */
  public AddCommand(User user) {
    super(user);
  }

  /**
   * Processes the subcommand provided by the user input and delegates it
   * to the appropriate method based on the subcommand string. This method switches
   * between different subcommands ("storage", "ingredient", "recipe") and calls the respective methods
   * for each of these subcommands. If the subcommand does not match any of the expected values,
   * an {@code IllegalArgumentException} is thrown.
   *
   * @throws IllegalArgumentException if the provided subcommand is not recognized.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "storage", "inventory" -> addStorage();
      case "ingredient" -> addIngredient();
      //      case "recipe" -> addRecipe();
      default -> illegalCommand();
    }
  }

  /**
   * Prints a success or failure message for an operation.
   *
   * @param success A boolean indicating whether the operation was successful.
   */
  private void printOperationMessage(boolean success) {
    if (success) {
      outputHandler.printOperationSuccessMessage("added", userInputString);
    } else
      outputHandler.printOperationFailedMessage("add", userInputString);
  }

  /**
   * Adds a new storage entry specified by the user's input.
   * This method prompts the user for a storage name and attempts to add it
   * to the user's storage map. If the addition is successful, an operation
   * success message is printed. If not, an operation failure message is printed.
   */
  private void addStorage() {
    requestInputIfNeeded("Please enter new storage name:");
    inventoryManager.createStorage(userInputString);
    printOperationMessage(true);
  }

  /**
   * Creates a new Ingredient using the inventory manager and adds it to the inventory.
   */
  private void addIngredient() {
    Ingredient createdIngredient = inventoryManager.createIngredient(userInputString);
    inventoryManager.addIngredient(createdIngredient);
  }

  /**
   * Creates and adds a new recipe using the recipe manager.
   */
  //  private void addRecipe() {
  //    Recipe createdRecipe = recipeManager.createRecipe();
  //    recipeManager.addRecipe(createdRecipe);
  //  }

}
