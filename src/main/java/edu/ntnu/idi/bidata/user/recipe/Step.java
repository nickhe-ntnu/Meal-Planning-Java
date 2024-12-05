package edu.ntnu.idi.bidata.user.recipe;

import edu.ntnu.idi.bidata.user.inventory.Measurement;

import java.util.List;

/**
 * Represents a step in a recipe with cooking instructions and ingredients.
 * Each step requires the instruction, but the list of measurements is optional.
 *
 * @author Nick Heggø
 * @version 2024-12-05
 */
public class Step {
  private String instruction;
  private List<Measurement> measurements;

  public Step(String instruction, List<Measurement> measurements) {
    setMeasurements(measurements);
    setInstruction(instruction);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getInstruction());
    if (hasMeasurements()) {
      measurements.forEach(measurement -> stringBuilder.append("\n   - ").append(measurement));
    }
    return stringBuilder.toString();
  }

  private boolean hasMeasurements() {
    return getMeasurements() != null && !getMeasurements().isEmpty();
  }

  /**
   * Retrieves the map of ingredient measurements for this step.
   *
   * @return a map where the key is a string representing the ingredient name, and the value is a Measurement object.
   */
  public List<Measurement> getMeasurements() {
    return measurements;
  }

  /**
   * Sets the list of measurements for the step.
   *
   * @param measurements the list of Measurement objects to set can be null or empty.
   */
  private void setMeasurements(List<Measurement> measurements) {
    this.measurements = measurements;
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
