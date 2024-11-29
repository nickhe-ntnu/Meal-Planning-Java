package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;

import java.util.List;

/**
 * Represents a step in a recipe with cooking instructions and ingredients.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class Step {
  private List<Measurement> measurementList;
  private String instruction;

  public Step(List<Measurement> measurementList, String instruction) {
    setMeasurementList(measurementList);
    setInstruction(instruction);
  }

  /**
   * Retrieves the list of ingredients for this step.
   *
   * @return the list of ingredients.
   */
  public List<Measurement> getMeasurementList() {
    return measurementList;
  }

  /**
   * Sets the list of ingredients for this step.
   *
   * @param measurementList List of ingredients to be set.
   */
  private void setMeasurementList(List<Measurement> measurementList) {
    if (measurementList == null) {
      throw new IllegalArgumentException("Measurements cannot be null.");
    }
    this.measurementList = measurementList;
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
  public void setInstruction(String instruction) {
    if (instruction == null) {
      throw new IllegalArgumentException("Step instruction cannot be null.");
    }
    if (instruction.isBlank()) {
      throw new IllegalArgumentException("Step instruction cannot be blank.");
    }
    this.instruction = instruction;
  }
}
