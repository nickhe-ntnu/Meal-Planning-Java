package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * ListCommand is a concrete implementation of the Command class that handles
 * various subcommands related to listing information, such as inventory, location,
 * recipes, ingredients, expired items, and available recipes.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class ListCommand extends Command {
  public ListCommand(User user) {
    super(user);
  }

  /**
   * Processes various subcommands related to listing information, such as inventory,
   * storage, recipes, ingredients, expired items, and available recipes.
   * This method interprets the user-provided subcommand and delegates the task
   * to the corresponding method.
   *
   * @throws IllegalArgumentException if the provided subcommand is unexpected or not recognized.
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
      case "command", "commands" -> outputHandler.printHelpMessage();
      case "name" -> listName();
      case "value", "values" -> listValue();
      default -> illegalCommand();
    }
  }

  private void listValue() {
    String message = inventoryManager.getTotalValue();
    outputHandler.printOutput(message);
  }

  private void listName() {
    outputHandler.printOutputWithLineBreak(inventoryManager.getStorageNameString());
  }

  /**
   * Lists the inventory of the user.
   * This method retrieves the user's storage data as a formatted string
   * and then prints it to the console with a line of separator characters.
   */
  private void listInventory() {
    outputHandler.printOutputWithLineBreak(inventoryManager.getInventoryString());
  }

  private void listStorage() {
    outputHandler.printOutputWithLineBreak(inventoryManager.getStorageString());
  }

  private void listRecipe() {
    // TODO
  }

  private void listIngredient() {
    // TODO
  }

  private void listExpired() {
    outputHandler.printOutputWithLineBreak(inventoryManager.getExpiredString());
  }

  private void listAvailableRecipe() {
    // TODO
  }

}
