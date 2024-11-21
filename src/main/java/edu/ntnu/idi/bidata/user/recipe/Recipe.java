package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * The Recipe class represents a recipe which consists of a series of cooking steps.
 * Each step contains a list of ingredients used in that particular step.
 *
 * @author Nick Heggø
 * @version 2024-11-08
 */
public class Recipe {
  private final List<List<Ingredient>> cookingSteps;
  private String nameOfRecipe;
  private String description;
  // first dimension of the list: each step in the recipe
  // second dimension of the list: ingredients that will be used in the current step

  /**
   * Constructs a Recipe with the specified name.
   *
   * @param nameOfRecipe the name to be assigned to the recipe
   */
  public Recipe(String nameOfRecipe) {
    cookingSteps = new ArrayList<>();
    this.nameOfRecipe = nameOfRecipe;
  }

  private void addSteps() {
    //    int iteration = 0;
    //    boolean adding = false;
    //    while ()
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe if it exists, otherwise an empty string
   */
  public String getNameOfRecipe() {
    return (nameOfRecipe != null) ? nameOfRecipe : "";
  }

  /**
   * Sets the name of the recipe.
   *
   * @param nameOfRecipe the name of the recipe to be set
   */
  public void setNameOfRecipe(String nameOfRecipe) {
    this.nameOfRecipe = nameOfRecipe;
  }

  /**
   * Returns the description of the recipe.
   *
   * @return the description if exists, otherwise an empty string
   */
  public String getDescription() {
    return (description != null) ? description : "";
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description the description of the recipe to be set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Adds a cooking step to the recipe.
   *
   * @param stepToBeAdded a list of ingredients that represents a single cooking step to be added to the recipe
   */
  public void addCookingSteps(List<Ingredient> stepToBeAdded) {
    cookingSteps.add(stepToBeAdded);
  }

}

