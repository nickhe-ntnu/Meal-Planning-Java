package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.UnitConverter;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.List;

/**
 * Represents a measurement with a name, amount, unit, and ingredient type.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class Measurement {
  private float amount;
  private ValidUnit validUnit;
  private IngredientType ingredientType; // set automatically, based on ValidUnit.

  /**
   * Default constructor for the Measurement class.
   * Initializes an empty Measurement instance.
   */
  public Measurement() {}

  /**
   * Constructs a Measurement with the specified name, amount, and unit.
   *
   * @param amount    the amount of the measurement must be non-negative
   * @param validUnit the unit of measurement must not be null or unknown
   */
  public Measurement(float amount, ValidUnit validUnit) {
    setAmount(amount);
    setValidUnit(validUnit);
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

    UnitConverter.autoMergeUnit(measurementToMerge, this.validUnit);
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
  public ValidUnit getValidUnit() {
    return validUnit;
  }

  /**
   * Sets the valid unit for this measurement.
   *
   * @param validUnit the unit of measurement
   * @throws IllegalArgumentException if the unit is null or UNKNOWN
   */
  public void setValidUnit(ValidUnit validUnit) {
    if (validUnit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    if (validUnit == ValidUnit.UNKNOWN) {
      throw new IllegalArgumentException("Invalid unit, please try again.");
    }
    this.validUnit = validUnit;
    autoSetIngredientType();
  }

  /**
   * Automatically sets the ingredient type based on the valid unit.
   * Throws IllegalArgumentException if the valid unit is null or unknown.
   */
  private void autoSetIngredientType() {
    if (validUnit == null) {
      throw new IllegalArgumentException("Ingredient type cannot be null.");
    }

    switch (getValidUnit()) {
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