package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;

import java.util.List;

/**
 * The FindCommand class is responsible for handling the "find" command issued by a user.
 * This command allows users to search for specific ingredients or recipes within the system.
 * It extends the abstract Command class and provides concrete implementations for processing
 * "find" subcommands.
 *
 * @author Nick Heggø
 * @version 2024-12-04
 */
public class FindCommand extends Command {

  /**
   * Constructs a new FindCommand object for the specified user.
   * The FindCommand class is used to handle the "find" command issued by a user.
   * This command allows users to search for specific ingredients or recipes within the system.
   *
   * @param user The user for whom the "find" command is being created and processed.
   */
  public FindCommand(User user) {
    super(user);
  }

  /**
   * Processes the subcommand of the find command by determining the action to take
   * based on the subcommand value.
   */
  @Override
  protected void processSubcommand() {
    switch (getSubcommand()) {
      case "ingredient" -> findIngredient();
      case "recipe" -> findRecipe();
      default -> illegalCommand();
    }
  }

  private void findIngredient() {
    setArgumentIfEmpty("Please enter the ingredient name to find:");
    List<Ingredient> storageContainsIngredient = getInventoryManager().findIngredientFromCurrent(getArgument());
    if (storageContainsIngredient.isEmpty()) {
      getOutputHandler().printOutputWithLineBreak(getArgument() + " isn't present at any of the storages.");
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getArgument()).append(" is present at:");
      storageContainsIngredient.forEach(storageName -> stringBuilder.append("\n  * ").append(storageName));
      getOutputHandler().printOutputWithLineBreak(stringBuilder.toString());
    }
  }

  private void findRecipe() {
    // TODO
  }

}
