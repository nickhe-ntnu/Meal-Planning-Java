package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The RemoveCommand class extends the Command class to handle "remove" operations
 * based on user-submitted subcommands. Depending on the specified subcommand, it
 * delegates the removal operation to the appropriate method.
 *
 * @author Nick Heggø
 * @version 2024-12-07
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
  public void execute() {
    switch (getSubcommand()) {
      case "storage" -> removeStorage();
      case "ingredient" -> removeIngredient();
      case "expired" -> removeExpired();
      case "recipe" -> removeRecipe();
      default -> illegalCommand();
    }
  }

  private void removeStorage() {
    setArgumentIfEmpty("Please enter the storage name to remove:");
    boolean success = getInventoryManager().removeStorage(getArgument());
    if (success) {
      getOutputHandler().printOutputWithLineBreak("Successfully removed " + getArgument() + " from the application.");
    } else {
      getOutputHandler().printOutput("Failed to remove " + getArgument() + ", please check if the name is misspelled.");
    }
  }

  private void removeExpired() {
    float sum = getInventoryManager().removeAllExpired();
    getOutputHandler().printOutputWithLineBreak("Value of " + sum + " kr worth of food is now been deleted.");
  }

  private void removeIngredient() {
    getOutputHandler().printOutput("List of all stock ingredients:");
    getOutputHandler().printList(getInventoryManager().getIngredientOverview(), "bullet");
    setArgumentIfEmpty("Please enter the ingredient name:");
    getInventoryManager().removeIngredientFromCurrent(getArgument());
  }

  private void removeRecipe() {
    //    recipeManager. //TODO
  }

}
