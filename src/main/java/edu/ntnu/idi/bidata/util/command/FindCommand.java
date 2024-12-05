package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.recipe.Recipe;
import edu.ntnu.idi.bidata.user.recipe.Step;
import edu.ntnu.idi.bidata.util.OutputHandler;

import java.util.List;

/**
 * The FindCommand class is responsible for handling the "find" command issued by a user.
 * This command allows users to search for specific ingredients or recipes within the system.
 * It extends the abstract Command class and provides concrete implementations for processing
 * "find" subcommands.
 *
 * @author Nick Heggø
 * @version 2024-12-05
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
    setArgumentIfEmpty("Please enter the recipe name:");
    List<Recipe> matchingRecipes = getRecipeManager().findRecipe(getArgument());
    OutputHandler outputHandler = getOutputHandler();
    if (matchingRecipes.isEmpty()) {
      outputHandler.printOutput("Cannot find matching recipe.");
    } else if (matchingRecipes.size() == 1) {
      printRecipeDetails(matchingRecipes.getFirst());
    } else {
      outputHandler.printList(matchingRecipes, "numbered");
      int index = -1;
      while (index >= matchingRecipes.size() || index < 1) {
        outputHandler.printInputPrompt("Please choose the recipe to show details:");
        index = getInputScanner().collectValidInteger();
      }
      printRecipeDetails(matchingRecipes.get(index - 1)); // Index start at 0, printed start at 1
    }
  }

  private void printRecipeDetails(Recipe recipe) {
    OutputHandler outputHandler = getOutputHandler();
    outputHandler.printOutput(recipe.getName() + ":");
    outputHandler.printOutput("\n" + recipe.getDescription() + "\n");
    List<Step> steps = recipe.getSteps();
    outputHandler.printList(steps, "bullet");
    outputHandler.printLineBreak();
  }

}
