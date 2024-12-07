package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cooking recipe composed of multiple steps, each with specific
 * instructions and required ingredients.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class Recipe {
  private final List<Step> steps;
  private String name;
  private String description;

  public Recipe() {
    steps = new ArrayList<>();
  }

  /**
   * Constructs a Recipe with the specified name.
   *
   * @param name the name to be assigned to the recipe
   */
  public Recipe(String name) {
    steps = new ArrayList<>();
    setName(name);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getName()).append(":")
        .append("\n").append("\n").append(getDescription()).append("\n");
    List<Step> stepList = getSteps();
    for (int i = 0; i < stepList.size(); i++) {
      int index = i + 1;
      stringBuilder.append("\n").append(index).append(Utility.getOrdinalSuffix(index)).append(" step: ")
          .append(stepList.get(i));
    }
    // the output will look like -> "1st step: " + step.toString()
    return stringBuilder.toString();
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
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
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
      throw new IllegalArgumentException("Description cannot be null.");
    }
    if (description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be empty.");
    }
    this.description = description;
  }

  /**
   * Retrieves the list of steps in the recipe.
   *
   * @return a list of Step objects representing the steps in the recipe.
   */
  public List<Step> getSteps() {
    return steps;
  }

  private void assertIndexWithInBounds(int index) {
    if (index >= steps.size()) {
      throw new IllegalArgumentException(
          "Step number out of bound, please provide the valid step number.");
    }
  }

}

