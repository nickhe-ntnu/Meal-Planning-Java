package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.input.UnitInput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick Heggø
 * @version 2024-12-07
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
  public Recipe constructRecipe(RecipeBuilder recipeBuilder) {
    outputHandler.printInputPrompt("Please enter the recipe name");
    recipeBuilder.setName(inputScanner.collectValidString());
    outputHandler.printInputPrompt("Please enter the recipe description:");
    recipeBuilder.setDescription(inputScanner.collectValidString());
    boolean completeAddingSteps = false;
    int stepCount = 1;
    while (!completeAddingSteps) {
      outputHandler.printInputPrompt("Please enter the instruction for step " + stepCount + ":");
      String instruction = inputScanner.collectValidString();
      ArrayList<Measurement> measurements = getMeasurements(stepCount);
      recipeBuilder.addStep(new Step(instruction, measurements));
      outputHandler.printInputPrompt("Do you want to add more steps? (Y/n");
      completeAddingSteps = !inputScanner.collectValidString().equalsIgnoreCase("y");
    }
    return recipeBuilder.getRecipe();
  }

  private ArrayList<Measurement> getMeasurements(int stepCount) {
    boolean completeAddingMeasurements = false;
    ArrayList<Measurement> measurements = null;
    while (!completeAddingMeasurements) {
      measurements = new ArrayList<>();
      outputHandler.printInputPrompt("Please enter the ingredient name:");
      String ingredientName = inputScanner.collectValidString();
      outputHandler.printInputPrompt("Please enter the amount + unit:");
      UnitInput unitInput = inputScanner.collectValidUnitInput();
      measurements.add(new Measurement(ingredientName, unitInput));
      outputHandler.printInputPrompt("Add more ingredients to step " + stepCount + " ? (Y/n)");
      completeAddingMeasurements = !inputScanner.collectValidString().equalsIgnoreCase("y");
    }
    return measurements;
  }

  public List<String> getRecipeOverview() {
    return cookBook.getRecipeOverview();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipeToBeAdded the recipe to be added to the cookbook
   */
  public void addRecipe(Recipe recipeToBeAdded) {
    cookBook.addRecipe(recipeToBeAdded);
  }

  public List<Recipe> findRecipe(String name) {
    return cookBook.findRecipesContainingName(name);
  }
}