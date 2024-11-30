package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;

import java.util.Map;

/**
 * Represents a step in a recipe with cooking instructions and ingredients.
 *
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class Step {
  private String instruction;
  private Map<String, Measurement> measurementMap;

  public Step(String instruction, Map<String, Measurement> measurementMap) {
    setMeasurementMap(measurementMap);
    setInstruction(instruction);
  }

  /**
   * Retrieves the map of ingredient measurements for this step.
   *
   * @return a map where the key is a string representing the ingredient name, and the value is a Measurement object.
   */
  public Map<String, Measurement> getMeasurementMap() {
    return measurementMap;
  }

  /**
   * Sets the map of ingredient measurements for this step.
   *
   * @param measurementMap a map where the key is a string representing the ingredient name,
   *                       and the value is a Measurement object; must not be null
   * @throws IllegalArgumentException if measurementMap is null
   */
  private void setMeasurementMap(Map<String, Measurement> measurementMap) {
    if (measurementMap == null) {
      throw new IllegalArgumentException("Measurements cannot be null.");
    }
    this.measurementMap = measurementMap;
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
