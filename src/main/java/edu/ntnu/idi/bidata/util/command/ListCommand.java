package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.util.OutputHandler;

import java.util.List;

/**
 * ListCommand is a concrete implementation of the Command class that handles
 * various subcommands related to listing information, such as inventory, location,
 * recipes, ingredients, expired items, and available recipes.
 *
 * @author Nick Heggø
 * @version 2024-12-07
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
  public void execute() {
    switch (getSubcommand()) {
      case "all" -> listAll();
      case "storage" -> listStorage();
      case "recipe" -> listRecipe();
      case "ingredient" -> listIngredient();
      case "expired" -> listExpired();
      case "available" -> listAvailableRecipe();
      case "command", "commands" -> getOutputHandler().printHelpMessage();
      case "name" -> listName();
      case "value", "values" -> listValue();
      default -> illegalCommand();
    }
  }

  private void listValue() {
    String message = getInventoryManager().getTotalValue();
    getOutputHandler().printOutput(message);
  }

  private void listName() {
    getOutputHandler().printOutputWithLineBreak(getInventoryManager().getStorageNameString());
  }

  /**
   * Lists the inventory of the user.
   * This method retrieves the user's storage data as a formatted string
   * and then prints it to the console with a line of separator characters.
   */
  private void listAll() {
    getOutputHandler().printOutputWithLineBreak(getInventoryManager().getInventoryString());
  }

  private void listStorage() {
    getOutputHandler().printOutputWithLineBreak(getInventoryManager().getStorageString());
  }

  private void listRecipe() {
    List<String> recipeList = getRecipeManager().getRecipeOverview();
    OutputHandler outputHandler = getOutputHandler();
    if (recipeList == null || recipeList.isEmpty()) {
      outputHandler.printOutput("There are currently 0 recipes in the system.");
    } else {
      outputHandler.printOutput("There are currently " + recipeList.size() + " recipes in the system.");
      outputHandler.printList(recipeList, "bullet");
    }
  }

  private void listIngredient() {
    // TODO
  }

  private void listExpired() {
    getOutputHandler().printOutputWithLineBreak(getInventoryManager().getExpiredString());
  }

  private void listAvailableRecipe() {
    // TODO
  }

}
