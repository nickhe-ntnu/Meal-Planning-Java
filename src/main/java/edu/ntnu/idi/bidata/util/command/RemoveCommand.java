package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The RemoveCommand class extends the Command class to handle "remove" operations
 * based on user-submitted subcommands. Depending on the specified subcommand, it
 * delegates the removal operation to the appropriate method.
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
public class RemoveCommand extends Command {
  public RemoveCommand(User user) {
    super(user);
  }

  /**
   * Handles the processing of subcommands specific to the "remove" command.
   * Based on the provided subcommand, this method delegates the removal
   * operation to the appropriate private method.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "storage" -> removeStorage();
      case "ingredient" -> removeIngredient();
      case "expired" -> removeExpired();
      case "recipe" -> removeRecipe();
      default -> illegalCommand();
    }
  }

  private void removeStorage() {
    requestInputIfNeeded("Please enter the storage name to remove:");
    // TODO
  }

  private void removeExpired() {
    inventoryManager.removeExpired();
  }

  private void removeIngredient() {
    String ingredientName = requestInputIfNeeded("Please enter the ingredient name:");
    inputScanner.collectValidString();
    inventoryManager.findAllIngredient(ingredientName);
  }

  private void removeRecipe() {
    // TODO
  }

}
