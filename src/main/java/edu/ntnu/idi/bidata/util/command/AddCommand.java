package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.recipe.Recipe;

/**
 * The AddCommand class extends the Command class and is responsible for handling
 * the "add" command by processing subcommands related to adding new items
 * such as locations and storage entries for a given user.
 *
 * @author Nick Heggø
 * @version 2024-12-04
 */
public class AddCommand extends Command {

  /**
   * Constructs a new AddCommand object for the given user. This constructor
   * initializes the necessary parts and processes the command related
   * to adding new entries.
   *
   * @param user The user for whom the add command is being created and processed.
   */
  public AddCommand(User user) {
    super(user);
  }

  /**
   * Processes subcommands related to adding new entries.
   * Depending on the subcommand, it delegates to appropriate methods.
   * If an unrecognized subcommand is provided, illegalCommand() is called.
   */
  @Override
  protected void processSubcommand() {
    switch (getSubcommand()) {
      case "storage", "inventory" -> addStorage();
      case "ingredient" -> addIngredient();
      case "recipe" -> addRecipe();
      default -> illegalCommand();
    }
  }

  /**
   * Adds a new ingredient storage by prompting for a name if necessary,
   * creating the storage in the inventory manager, and printing the operation status.
   */
  private void addStorage() {
    setArgumentIfEmpty("Please enter new storage name:");
    getInventoryManager().createIngredientStorage(getArgument());
    getOutputHandler().printOperationStatus(true, "added", getArgument());
  }

  /**
   * Creates a new Ingredient using the inventory manager and adds it to the inventory.
   */
  private void addIngredient() {
    setArgumentIfEmpty("Please enter the ingredient name:");
    Ingredient createdIngredient = getInventoryManager().createIngredient(getArgument());
    getInventoryManager().addIngredientToCurrentStorage(createdIngredient);
    getOutputHandler().printOperationStatus(true, "added", getArgument());
  }

  /**
   * Creates and adds a new recipe using the recipe manager.
   */
  private void addRecipe() {
    setArgumentIfEmpty("Please enter the ingredient name:");
    Recipe createdRecipe = getRecipeManager().createRecipe(getArgument());
    getRecipeManager().addRecipe(createdRecipe);
  }

}
