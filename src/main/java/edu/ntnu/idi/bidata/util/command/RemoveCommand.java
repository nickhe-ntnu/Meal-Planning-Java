package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

import java.time.LocalDate;

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
      case "recipe" -> removeRecipe();
    }
  }

  private void removeStorage() {
    getInputString("Please enter name and expiry date to remove: {Ingredient Name} {YYYY-MM-DD}");
    // TODO
  }

  private void removeIngredient() {
    String inputString = getInputString("Please enter name and expiry date to remove: {YYYY-MM-DD} {Ingredient Name}");
    String[] token = inputString.split("\\s", 2);
    user.getCurrentStorage().removeIngredient(token[1], LocalDate.parse(token[0]));
    // TODO let user choose how much to remove, instead of remove everything at once
  }

  private void removeRecipe() {
    // TODO
  }

}
