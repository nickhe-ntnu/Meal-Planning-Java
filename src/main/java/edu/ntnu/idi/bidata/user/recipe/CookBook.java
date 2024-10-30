package edu.ntnu.idi.bidata.user.recipe;

import java.util.HashSet;

public class CookBook {
  HashSet<Recipe> recipes;

  public CookBook() {
    recipes = new HashSet<>();
  }

  /**
   * Adds a recipe to the cookbook if it is not already present.
   *
   * @param recipe the recipe to be added
   * @return true if the recipe was added, false if it was already in the cookbook
   */
  public boolean addRecipe(Recipe recipe) {
    boolean isAdded = false;
    if (!isRecipeInBook(recipe)) {
      recipes.add(recipe);
      isAdded = true;
    }
    return isAdded;
  }

  /**
   * Checks if a given recipe is present in the cookbook.
   *
   * @param recipe the recipe to be checked
   * @return true if the recipe is in the cookbook, false otherwise
   */
  public boolean isRecipeInBook(Recipe recipe) {
    return recipes.contains(recipe);
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipe the recipe to be removed
   */
  public void removeRecipe(Recipe recipe) {

  }

  /**
   * Lists all recipes in the cookbook.
   * This method iterates over the recipes stored in the CookBook instance
   * and prints out details of each recipe, such as their names.
   */
  public void listRecipes() {

  }

  /**
   * Lists all unique ingredients from the recipes contained in the cookbook.
   * This method iterates over all recipes in the cookbook, collects their ingredients,
   * and prints out a list of ingredients without duplicates.
   */
  public void listIngredients() {

  }

  /**
   * The cookbook list all the recipe that has enough ingredient for
   * The method will iterate over all the recipes stored in the CookBook instance
   * and print them, typically displaying their names or other relevant details.
   */
  public void listAvailableRecipes() {

  }

}
