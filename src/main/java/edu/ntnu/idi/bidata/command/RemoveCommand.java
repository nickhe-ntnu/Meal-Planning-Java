package edu.ntnu.idi.bidata.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The RemoveCommand class extends the Command class to handle "remove" operations
 * based on user-submitted subcommands. Depending on the specified subcommand, it
 * delegates the removal operation to the appropriate method.
 *
 * @author Nick Heggø
 * @version 2024-11-02
 */
public class RemoveCommand extends Command {
  public RemoveCommand(User user) {
    super(user);
  }

  /**
   * Processes the user input subcommand and delegates the removal operation
   * to the appropriate method based on the subcommand.
   * This method overrides the abstract method in the Command class and defines
   * the specific behavior for handling "remove" subcommands. Depending on the
   * value of {@code userInputSubcommand}, it will call one of the following methods:
   * <ul>
   *   <li>{@code removeLocation} for subcommand "location"</li>
   *   <li>{@code removeStorage} for subcommand "storage"</li>
   *   <li>{@code removeIngredient} for subcommand "ingredient"</li>
   *   <li>{@code removeRecipe} for subcommand "recipe"</li>
   * </ul>
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "location" -> removeLocation();
      case "storage" -> removeStorage();
      case "ingredient" -> removeIngredient();
      case "recipe" -> removeRecipe();
    }
  }

  private void removeLocation() {
    // TODO
  }

  private void removeStorage() {
    // TODO
  }

  private void removeIngredient() {
    // TODO
  }

  private void removeRecipe() {
    // TODO
  }

}
