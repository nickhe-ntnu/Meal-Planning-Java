package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * ListCommand is a concrete implementation of the Command class that handles
 * various subcommands related to listing information, such as inventory, location,
 * recipes, ingredients, expired items, and available recipes.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class ListCommand extends Command {
  public ListCommand(User user) {
    super(user);
  }

  /**
   * Processes the subcommand specified by the user's input.
   * This method overrides the abstract method in the parent Command class to provide specific
   * implementations for different subcommands related to listing various types of information,
   * such as inventory, location, recipe, ingredient, expired items, and available recipes.
   * If the subcommand is not recognized, it throws an IllegalArgumentException.
   * The recognized subcommands are:
   * - "inventory": Calls the listInventory() method.
   * - "location": Calls the listLocation() method.
   * - "recipe": Calls the listRecipe() method.
   * - "ingredient": Calls the listIngredient() method.
   * - "expired": Calls the listExpired() method.
   * - "available": Calls the listAvailableRecipe() method.
   * Throws:
   * - IllegalArgumentException: if the subcommand is not recognized.
   *
   * @throws IllegalArgumentException if an unexpected subcommand is provided
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "inventory" -> listInventory();
      case "storage" -> listStorage();
      case "recipe" -> listRecipe();
      case "ingredient" -> listIngredient();
      case "expired" -> listExpired();
      case "available" -> listAvailableRecipe();
      default -> throw new IllegalArgumentException("Unexpected command:  list " + userInputSubcommand);
    }
  }

  private void listInventory() {
    outputHandler.printOutputWithLineBreak(user.getAllStorageString());
  }

  private void listStorage() {

  }


  private void listRecipe() {
    // TODO
  }

  private void listIngredient() {
    // TODO
  }

  private void listExpired() {
    // TODO
  }

  private void listAvailableRecipe() {
    // TODO
  }

}
