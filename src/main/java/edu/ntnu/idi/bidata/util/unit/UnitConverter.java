package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.Measurement;
import edu.ntnu.idi.bidata.util.command.Utility;

import java.util.List;

/**
 * Class for converting between different units of measurement.
 * The UnitConverter class provides methods to convert values from one
 * unit of measurement to another, using the defined ValidUnit
 * enumeration which includes units for weight (KG, G) and volume (L, DL, ML).
 *
 * @author Nick Heggø
 * @version 2024-11-28
 */
public class UnitConverter {

  public UnitConverter() {}

  /**
   * Converts the given measurement to a standard unit based on its ingredient type.
   * If the type is "SOLID", it converts the measurement to kilograms.
   * If the type is "LIQUID", it converts the measurement to liters.
   *
   * @param measurement the measurement to be converted; can be null
   */
  public void convertToStandard(Measurement measurement) {
    String ingredientType = "";
    if (measurement != null) {
      ingredientType = measurement.getIngredientType();
    }
    if (ingredientType.equals("SOLID")) {
      convertToKG(measurement);
    } else if (ingredientType.equals("LIQUID")) {
      convertToLiter(measurement);
    }
  }

  public List<Object> getStandardData(Measurement measurement) {
    Utility.assertNonNull(measurement);
    String ingredientType = measurement.getIngredientType();
    List<Object> data = null;
    if (ingredientType.equals("SOLID")) {
      data = convertSolid(measurement, ValidUnit.KG, 1, 0.001f);
    } else if (ingredientType.equals("LIQUID")) {
      data = convertLiquid(measurement, ValidUnit.L, 1, 0.1f, 0.001f);
    }
    return data;
  }

  /**
   * Converts the measurement of the given ingredient to the specified target unit.
   *
   * @param ingredient the ingredient whose measurement is to be converted; must not be null
   * @param targetUnit the unit to which the ingredient's measurement should be converted; must not be null
   */
  public void convertIngredient(Ingredient ingredient, ValidUnit targetUnit) {
    Measurement measurement = ingredient.getMeasurement();
    autoMergeUnit(measurement, targetUnit);
  }

  /**
   * Converts the given measurement to grams.
   *
   * @param measurement the measurement to be converted; must not be null
   */
  public void convertToGrams(Measurement measurement) {
    List<Object> data = convertSolid(measurement, ValidUnit.G, 1000, 1);
    updateMeasurement(measurement, data);
  }

  /**
   * Converts the given measurement to kilograms if it is a solid.
   *
   * @param measurement the measurement to be converted; must not be null
   */
  public void convertToKG(Measurement measurement) {
    List<Object> data = convertSolid(measurement, ValidUnit.KG, 1, 0.001f);
    updateMeasurement(measurement, data);
  }

  /**
   * Converts the given measurement to liters.
   *
   * @param measurement the measurement to be converted; must not be null
   */
  public void convertToLiter(Measurement measurement) {
    List<Object> data = convertLiquid(measurement, ValidUnit.L, 1, 0.1f, 0.001f);
    updateMeasurement(measurement, data);
  }

  /**
   * Converts the given measurement to deciliters.
   *
   * @param measurement the measurement to be converted; must not be null
   */
  public void convertToDeciLiter(Measurement measurement) {
    List<Object> data = convertLiquid(measurement, ValidUnit.DL, 10, 1f, 0.01f);
    updateMeasurement(measurement, data);
  }

  /**
   * Converts the given measurement to milliliters.
   *
   * @param measurement the measurement to be converted; must not be null
   */
  public void convertToMilliLiter(Measurement measurement) {
    List<Object> data = convertLiquid(measurement, ValidUnit.ML, 1000, 100f, 1f);
    updateMeasurement(measurement, data);
  }

  /**
   * Converts the given measurement to the specified target unit based on its current valid unit.
   *
   * @param measurement the measurement to be converted; must not be null
   * @param targetUnit  the unit to convert the measurement to; must not be null
   * @throws IllegalArgumentException if the conversion between the current unit and the target unit is not allowed
   */
  public void autoMergeUnit(Measurement measurement, ValidUnit targetUnit) {
    if (targetUnit != null && measurement.getValidUnit() != null) {
      switch (targetUnit) {
        case L -> convertToLiter(measurement);
        case DL -> convertToDeciLiter(measurement);
        case ML -> convertToMilliLiter(measurement);
        case KG -> convertToKG(measurement);
        case G -> convertToGrams(measurement);
        default ->
            throw new IllegalArgumentException("Illegal operation: cannot convert from " + measurement.getValidUnit() + " to " + targetUnit);
      }
    }
  }

  public void updateMeasurement(Measurement sourceMeasurement, List<Object> data) {
    sourceMeasurement.setAmount((Float) data.getFirst());
    sourceMeasurement.setValidUnit((ValidUnit) data.getLast());
  }

  /**
   * Converts a given solid measurement to the specified target unit using specified conversion factors.
   *
   * @param measurement  the measurement to be converted; must not be null
   * @param targetUnit   the unit to convert the measurement to; must not be null
   * @param factorFromKG conversion factor to apply when the current unit is kilograms
   * @param factorFromG  conversion factor to apply when the current unit is grams
   * @return a list containing the converted amount and target unit
   * @throws IllegalArgumentException if the conversion between the current unit and the target unit is not allowed
   */
  private List<Object> convertSolid(Measurement measurement, ValidUnit targetUnit, float factorFromKG, float factorFromG) {
    float amount = measurement.getAmount();
    ValidUnit currentUnit = measurement.getValidUnit();
    switch (currentUnit) {
      case KG -> amount *= factorFromKG;
      case G -> amount *= factorFromG;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    amount = roundToTwoDecimals(amount);
    return List.of(amount, targetUnit);
  }

  /**
   * Converts a given liquid measurement to the specified target unit using specified conversion factors.
   *
   * @param sourceMeasurement the measurement to be converted; must not be null
   * @param targetUnit        the unit to convert the measurement to; must not be null
   * @param factorFromL       conversion factor to apply when the current unit is liters
   * @param factorFromDL      conversion factor to apply when the current unit is deciliters
   * @param factorFromML      conversion factor to apply when the current unit is milliliters
   * @return a list containing the converted amount and target unit
   * @throws IllegalArgumentException if the conversion between the current unit and the target unit is not allowed
   */
  private List<Object> convertLiquid(Measurement sourceMeasurement, ValidUnit targetUnit, float factorFromL, float factorFromDL, float factorFromML) {
    float amount = sourceMeasurement.getAmount();
    ValidUnit currentUnit = sourceMeasurement.getValidUnit();
    switch (currentUnit) {
      case L -> amount *= factorFromL;
      case DL -> amount *= factorFromDL;
      case ML -> amount *= factorFromML;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    amount = roundToTwoDecimals(amount);
    return List.of(amount, targetUnit);
  }

  /**
   * Rounds a given float value to two decimal places.
   * The method multiplies the value by 100, rounds it, and then divides by 100.
   *
   * @param value the float value to be rounded
   * @return the rounded float value with two decimal places
   */
  private float roundToTwoDecimals(float value) {
    return Math.round(value * 100) / 100.0f;
  }

}