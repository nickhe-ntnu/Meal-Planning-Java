package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.Utility;

import java.util.HashMap;

/**
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class RecipeManager {

  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  private final CookBook cookBook;

  /**
   * Constructs a RecipeManager with the provided inputScanner and outputHandler.
   * Initializes a new CookBook instance.
   *
   * @param inputScanner  an InputScanner instance for reading user inputs
   * @param outputHandler an OutputHandler instance for displaying output to users
   */
  public RecipeManager(InputScanner inputScanner, OutputHandler outputHandler) {
    this.inputScanner = inputScanner;
    this.outputHandler = outputHandler;
    cookBook = new CookBook();
  }

  /**
   * Creates a new recipe by prompting the user for the recipe details.
   *
   * @return the newly created Recipe object
   */
  public Recipe createRecipe(String name) {
    String recipeName = name;
    if (recipeName == null) {
      outputHandler.printInputPrompt("Please enter the recipe name:");
      inputScanner.collectValidString();
    }
    Recipe createdRecipe = builder(new Recipe(name));
    return createdRecipe;
  }

  private String getValidDescription() {
    outputHandler.printOutput("Please enter the recipe description:");
    return inputScanner.collectValidString();
  }

  /**
   * Modifies the given recipe by updating its description and collecting ingredients.
   *
   * @param recipeToModify the Recipe object to be modified
   */
  private Recipe builder(Recipe recipeToModify) {
    boolean finished = false;
    recipeToModify.setDescription(getValidDescription());
    int stepCount = 1;
    while (!finished) {
      createStep(stepCount);
      stepCount++;
      outputHandler.printInputPrompt("Continue adding more steps? (Y/n)");
      finished = !inputScanner.nextLine().equalsIgnoreCase("y");
    }
    return recipeToModify; //FIXME incomplete methods.
  }

  private Step createStep(int stepCount) {
    outputHandler.printInputPrompt("Please enter the "
        + stepCount + Utility.getOrdinalSuffix(stepCount) + " step instruction:");
    String instruction = inputScanner.collectValidString();
    HashMap<String, Measurement> measurementMap = new HashMap<>();
    boolean finished = false;
    while (!finished) {

    }

    return new Step(instruction, measurementMap); //FIXME incomplete method
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipeToBeAdded the recipe to be added to the cookbook
   */
  public void addRecipe(Recipe recipeToBeAdded) {
    cookBook.addRecipe(recipeToBeAdded);
  }
}