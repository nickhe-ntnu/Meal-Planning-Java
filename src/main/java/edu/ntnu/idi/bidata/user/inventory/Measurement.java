package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.input.UnitInput;
import edu.ntnu.idi.bidata.util.unit.UnitConverter;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.List;
import java.util.Objects;

/**
 * Represents a measurement with a name, amount, unit, and ingredient type.
 *
 * @author Nick Heggø
 * @version 2024-12-05
 */
public class Measurement {
  private String name; // Ingredient Name
  private float amount;
  private ValidUnit unit;
  private IngredientType ingredientType; // set automatically, based on ValidUnit.

  public Measurement() {
  }

  public Measurement(String name, UnitInput input) {
    setName(name);
    setAmount(input.getAmount());
    setUnit(input.getUnit());
  }

  /**
   * Constructs a Measurement with the specified name, amount, and unit.
   *
   * @param amount the amount of the measurement must be non-negative
   * @param unit   the unit of measurement must not be null or unknown
   */
  public Measurement(String name, float amount, ValidUnit unit) {
    setName(name);
    setAmount(amount);
    setUnit(unit);
  }

  @Override
  public String toString() {

    return getAmount() + " " + getUnit().name().toLowerCase() + " " + getName();
  }

  /**
   * Generates a hash code for the Measurement object based on its fields.
   *
   * @return an integer hash code derived from amount, validUnit, and ingredientType.
   */
  @Override

  public int hashCode() {
    return Objects.hash(name, amount, unit, ingredientType);
  }

  /**
   * Compares this measurement to the specified object for equality.
   *
   * @param obj the object to compare with this measurement
   * @return true if the specified object is equal to this measurement, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Measurement m = (Measurement) obj;
    return Float.compare(getAmount(), m.getAmount()) == 0
        && getName().equals(m.getName())
        && getUnit().equals(m.getUnit());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Object> getStandardMeasurement() {
    return UnitConverter.getStandardData(this);
  }

  /**
   * Merges the specified Measurement instance with the current one.
   * The units are automatically adjusted for consistency.
   * If the merged amount reaches or exceeds 1000, it converts to a standard unit.
   *
   * @param measurementToMerge the Measurement instance to merge with the current one,
   *                           must not be null
   * @throws IllegalArgumentException if measurementToMerge is null
   */
  public void merge(Measurement measurementToMerge) {
    if (measurementToMerge == null) {
      throw new IllegalArgumentException("Measurement cannot be null!");
    }

    UnitConverter.autoMergeUnit(measurementToMerge, this.unit);
    float mergedAmount = this.getAmount() + measurementToMerge.getAmount();
    this.setAmount(mergedAmount);
    if (mergedAmount >= 1000) {
      UnitConverter.convertToStandard(this);
    }
  }

  /**
   * Retrieves the amount of the measurement.
   *
   * @return the amount as a float.
   */
  public float getAmount() {
    return amount;
  }

  /**
   * Sets the amount for the measurement.
   * Throws IllegalArgumentException if the amount is negative.
   *
   * @param amount the amount to set must be non-negative
   */
  public void setAmount(float amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    this.amount = amount;
  }

  /**
   * Retrieves the type of ingredient as a string.
   *
   * @return the ingredient type as a string.
   */
  public String getIngredientType() {
    return ingredientType.toString();
  }

  /**
   * Retrieves the valid unit of measurement.
   *
   * @return the valid unit as an instance of ValidUnit.
   */
  public ValidUnit getUnit() {
    return unit;
  }

  /**
   * Sets the valid unit for this measurement.
   *
   * @param unit the unit of measurement
   * @throws IllegalArgumentException if the unit is null or UNKNOWN
   */
  public void setUnit(ValidUnit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    if (unit == ValidUnit.UNKNOWN) {
      throw new IllegalArgumentException("Invalid unit, please try again.");
    }
    this.unit = unit;
    autoSetIngredientType();
  }

  /**
   * Automatically sets the ingredient type based on the valid unit.
   * Throws IllegalArgumentException if the valid unit is null or unknown.
   */
  private void autoSetIngredientType() {
    if (unit == null) {
      throw new IllegalArgumentException("Ingredient type cannot be null.");
    }

    switch (getUnit()) {
      case KG, G -> this.ingredientType = IngredientType.SOLID;
      case L, DL, ML -> this.ingredientType = IngredientType.LIQUID;
      default -> throw new IllegalArgumentException("Measurement Unit is unknown.");
    }
  }

  /**
   * Represents the property of ingredient.
   */
  private enum IngredientType {
    SOLID,
    LIQUID
  }
}