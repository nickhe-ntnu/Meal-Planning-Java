package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;

import java.util.List;

/**
 * Represents a step in a recipe with cooking instructions and ingredients.
 *
 * @author Nick Heggø
 * @version 2024-11-27
 */
public class Step {
  private String instruction;
  private List<Measurement> ingredientList;

  /**
   * Constructs a Step with provided cooking instruction and list of ingredients.
   *
   * @param instruction    The cooking instruction for this step.
   * @param ingredientList List of ingredients required for this step.
   */
  public Step(String instruction, List<Measurement> ingredientList) {
    setInstruction(instruction);
    setIngredientList(ingredientList);
  }

  /**
   * Retrieves the list of ingredients for this step.
   *
   * @return the list of ingredients.
   */
  public List<Measurement> getIngredientList() {
    return ingredientList;
  }

  /**
   * Sets the list of ingredients for this step.
   *
   * @param ingredientList List of ingredients to be set.
   */
  private void setIngredientList(List<Measurement> ingredientList) {
    this.ingredientList = ingredientList;
  }

  /**
   * Retrieves the cooking instruction for this step.
   *
   * @return the cooking instruction.
   */
  public String getInstruction() {
    return instruction;
  }

  /**
   * Sets the cooking instruction for this step.
   *
   * @param instruction The cooking instruction to be set.
   */
  private void setInstruction(String instruction) {
    this.instruction = instruction;
  }
}
