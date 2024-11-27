package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

import java.util.List;

/**
 * Represents a step in a recipe with cooking instructions and ingredients.
 *
 * @author Nick Heggø
 * @version 2024-11-27
 */
public class Step {
  private String instruction;
  private List<Ingredient> ingredientList;

  public Step(String instruction, List<Ingredient> ingredientList) {
    setInstruction(instruction);
    setIngredientList(ingredientList);
  }

  public void setIngredientList(List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public List<Ingredient> getIngredientList() {
    return ingredientList;
  }

  public String getInstruction() {
    return instruction;
  }
}
