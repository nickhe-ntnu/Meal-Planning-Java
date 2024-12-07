package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.recipe.Recipe;
import edu.ntnu.idi.bidata.util.OutputHandler;

import java.util.List;

/**
 * The FindCommand class is responsible for handling the "find" command issued by a user.
 * This command allows users to search for specific ingredients or recipes within the system.
 * It extends the abstract Command class and provides concrete implementations for processing
 * "find" subcommands.
 *
 * @author Nick Heggø
 * @version 2024-12-07
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
  public void execute() {
    switch (getSubcommand()) {
      case "ingredient" -> findIngredient();
      case "recipe" -> findRecipe();
      default -> illegalCommand();
    }
  }

  private void findIngredient() {
    setArgumentIfEmpty("Please enter the ingredient name to find:");
    List<Ingredient> storageContainsIngredient = getInventoryManager().findIngredientFromCurrent(getArgument());
    // first case, none matching.
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
    setArgumentIfEmpty("Please enter the recipe name:");
    List<Recipe> matchingRecipes = getRecipeManager().findRecipe(getArgument());
    OutputHandler outputHandler = getOutputHandler();

    if (matchingRecipes.isEmpty()) {
      // first case, none matching.
      outputHandler.printOutput("Cannot find matching recipe.");
    } else if (matchingRecipes.size() == 1) {
      // in the second case, only one match found
      printRecipeDetails(matchingRecipes.getFirst());
    } else {
      // final case, multiple matches found
      List<String> names = matchingRecipes.stream().map(Recipe::getName).toList();
      outputHandler.printList(names, "numbered");
      int index = -1;
      while (index >= matchingRecipes.size() || index < 1) {
        outputHandler.printInputPrompt("Please choose the recipe to show details:");
        index = getInputScanner().collectValidInteger();
      }
      printRecipeDetails(matchingRecipes.get(index - 1)); // Index start at 0, printed start at 1
    }
  }

  private void printRecipeDetails(Recipe recipe) {
    getOutputHandler().printOutput(recipe.toString());
  }

}
