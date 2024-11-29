package edu.ntnu.idi.bidata.user.recipe;

import java.util.HashSet;

/**
 * @author Nick Heggø
 * @version 2024-11-29
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
}
