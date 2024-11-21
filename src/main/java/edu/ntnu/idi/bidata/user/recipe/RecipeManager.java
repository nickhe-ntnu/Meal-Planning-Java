package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;

public class RecipeManager {

  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  private final CookBook cookBook;

  public RecipeManager(InputScanner inputScanner, OutputHandler outputHandler) {
    this.inputScanner = inputScanner;
    this.outputHandler = outputHandler;
    cookBook = new CookBook();
  }

  public void addRecipe() {
    Recipe recipeToBeAdded = createRecipeFromInput();
    cookBook.addRecipe(recipeToBeAdded);
  }

  private Recipe createRecipeFromInput() {
    outputHandler.printInputPrompt("Please enter the recipe name:");
    String recipeName = inputScanner.getValidString("Invalid try again.");
    Recipe createdRecipe = new Recipe(recipeName);
    builder(createdRecipe);
    return createdRecipe;
  }

  private void builder(Recipe recipeToModify) {
    boolean finished = false;
    System.out.println("Please enter the recipe description:");
    recipeToModify.setDescription(inputScanner.getInputString());
    do {
      int iteration = 1;
      System.out.println("Please enter the ingredient for the " + iteration + ". step: 'amount' 'unit' 'name'");
      //      inputScanner.getValidStep()
      System.out.println("Type 'exit' to terminate the ingredient wizard");
      finished = inputScanner.getValidString().equals("exit");
      iteration++;
    } while (!finished);
    System.out.println("Please add ingredient for ");
  }

}
