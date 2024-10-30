package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

import java.util.HashSet;

public class Recipe {
  private String name;
  private HashSet<Ingredient> ingredients;

  /**
   * Constructs a new Recipe instance with the specified name.
   *
   * @param name the name of the recipe
   * @throws IllegalArgumentException if the name is null or empty
   */
  public Recipe(String name) {
    setName(name);
    ingredients = new HashSet<>();
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the set of ingredients in the recipe.
   *
   * @return a HashSet containing the ingredients
   */
  public HashSet<Ingredient> getIngredients() {
    return ingredients;
  }

  /**
   * Sets the name of the recipe.
   *
   * @param name the name to be set
   */
  public void setName(String name) {
    this.name = name;
  }

}
