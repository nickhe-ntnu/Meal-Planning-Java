package edu.ntnu.idi.bidata.user.recipe;

import java.util.HashSet;

/**
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class CookBook {

  private final HashSet<Recipe> recipes;

  public CookBook() {
    recipes = new HashSet<>();
  }

  /**
   * Checks if a given recipe is present in the cookbook.
   *
   * @param recipe the recipe to be checked
   * @return true if the recipe is in the cookbook, false otherwise
   */
  public boolean isRecipePresent(Recipe recipe) {
    return recipes.contains(recipe);
  }

  public void addRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe is null.");
    }
    if (isRecipePresent(recipe)) {
      throw new IllegalArgumentException("Recipe already exist!");
    }
    recipes.add(recipe);
  }
}
