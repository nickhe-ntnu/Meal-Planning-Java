package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;

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
  public Recipe createRecipe() {
    Recipe createdRecipe = inputRecipeDetails();
    boolean complete = false;
    while (!complete) {
      complete = builder(createdRecipe);
    }
    return createdRecipe;
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipeToBeAdded the recipe to be added to the cookbook
   */
  public void addRecipe(Recipe recipeToBeAdded) {
    Recipe createdRecipe = createRecipe();
    cookBook.addRecipe(recipeToBeAdded);
  }

  /**
   * Prompts the user for recipe details and creates a new Recipe object.
   *
   * @return the newly created Recipe object
   */
  private Recipe inputRecipeDetails() {
    outputHandler.printInputPrompt("Please enter the recipe name:");
    String recipeName = inputScanner.collectValidString();
    Recipe createdRecipe = new Recipe(recipeName);
    builder(createdRecipe);
    return createdRecipe;
  }

  /**
   * Modifies the given recipe by updating its description and collecting ingredients.
   *
   * @param recipeToModify the Recipe object to be modified
   */
  private boolean builder(Recipe recipeToModify) {
    boolean finished = false;
    System.out.println("Please enter the recipe description:");
    recipeToModify.setDescription(inputScanner.collectValidString());
    while (!finished) {
      int iteration = 1;
      System.out.println("Please enter the ingredient for the " + iteration + ". step: 'amount' 'unit' 'name'");
      //      inputScanner.getValidStep()
      System.out.println("Type 'exit' to terminate the ingredient wizard");
      finished = inputScanner.collectValidString().equals("exit");
      iteration++;
    }
    System.out.println("Please add ingredient for ");
    return false;
  }

}
