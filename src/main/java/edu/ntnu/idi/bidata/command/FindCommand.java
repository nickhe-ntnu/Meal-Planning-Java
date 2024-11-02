package edu.ntnu.idi.bidata.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The FindCommand class is responsible for handling the "find" command issued by a user.
 * This command allows users to search for specific ingredients or recipes within the system.
 * It extends the abstract Command class and provides concrete implementations for processing
 * "find" subcommands.
 *
 * @author Nick Heggø
 * @version 2024-11-02
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
   * Handles the processing of subcommands for the "find" command.
   * This method determines the specific search action to perform based
   * on the user's input subcommand. It supports finding ingredients and recipes.
   * If the user input subcommand is "ingredient", it delegates the action to {@link #findIngredient()}.
   * If the user input subcommand is "recipe", it delegates the action to {@link #findRecipe()}.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "ingredient" -> findIngredient();
      case "recipe" -> findRecipe();
    }
  }

  private void findIngredient() {
    // TODO
  }

  private void findRecipe() {
    // TODO
  }

}
