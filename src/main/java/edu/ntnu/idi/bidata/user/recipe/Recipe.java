package edu.ntnu.idi.bidata.user.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cooking recipe composed of multiple steps, each with specific
 * instructions and required ingredients.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class Recipe {
  private final List<Step> steps;
  private String name;
  private String description;
  // first dimension of the list: each step in the recipe
  // second dimension of the list: ingredients that will be used in the current step

  /**
   * Constructs a Recipe with the specified name.
   *
   * @param name the name to be assigned to the recipe
   */
  public Recipe(String name) {
    steps = new ArrayList<>();
    setNameOfRecipe(name);
  }

  /**
   * Retrieves the name of the recipe.
   *
   * @return the name of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the recipe.
   *
   * @param name the new name to assign; must not be null
   * @throws IllegalArgumentException if the provided name is null
   */
  public void setNameOfRecipe(String name) {
    if (this.name == null) {
      throw new IllegalArgumentException("Name can not be null.");
    }
    this.name = name;
  }

  /**
   * Retrieves the description of the recipe.
   *
   * @return the description of the recipe, or null if not set
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description the description to be set; must not be null or empty
   * @throws IllegalArgumentException if the description is null or empty
   */
  public void setDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("Description can not be null.");
    }
    if (description.isBlank()) {
      throw new IllegalArgumentException("Description can not be empty.");
    }
    this.description = description;
  }

  /**
   * Adds a cooking step to the recipe.
   *
   * @param step the step to be added, which includes instructions and ingredients
   */
  public void addStep(Step step) {
    steps.add(step);
  }

  public void removeStep(int stepNumber) {
    assertIndexWithInBounds(stepNumber);
    steps.remove(stepNumber);
  }

  public Step getStep(int stepNumber) {
    assertIndexWithInBounds(stepNumber);
    return steps.get(stepNumber);
  }

  private void assertIndexWithInBounds(int index) {
    if (index >= steps.size()) {
      throw new IllegalArgumentException(
          "Step number out of bound, please provide the valid step number.");
    }
  }

}

