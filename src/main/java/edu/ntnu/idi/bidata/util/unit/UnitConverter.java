package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.Measurement;

/**
 * Class for converting between different units of measurement.
 * The UnitConverter class provides methods to convert values from one
 * unit of measurement to another, using the defined ValidUnit
 * enumeration which includes units for weight (KG, G) and volume (L, DL, ML).
 *
 * @author Nick Heggø
 * @version 2024-11-27
 */
public class UnitConverter {

  public UnitConverter() {
  }

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

  public void convertIngredient(Ingredient ingredient, ValidUnit targetUnit) {
    Measurement measurement = ingredient.getMeasurement();
    autoMergeUnit(measurement, targetUnit);
  }

  public void convertToGrams(Measurement measurement) {
    convertSolid(measurement, ValidUnit.G, 1000, 1);
  }

  public void convertToKG(Measurement measurement) {
    convertSolid(measurement, ValidUnit.KG, 1, 0.001f);
  }

  public void convertToLiter(Measurement measurement) {
    convertLiquid(measurement, ValidUnit.L, 1, 0.1f, 0.001f);
  }

  public void convertToDeciLiter(Measurement measurement) {
    convertLiquid(measurement, ValidUnit.DL, 10, 1f, 0.01f);
  }

  public void convertToMilliLiter(Measurement measurement) {
    convertLiquid(measurement, ValidUnit.ML, 1000, 100f, 1f);
  }

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

  private void convertSolid(Measurement measurement, ValidUnit targetUnit, float factorFromKG, float factorFromG) {
    float amount = measurement.getAmount();
    ValidUnit currentUnit = measurement.getValidUnit();
    switch (currentUnit) {
      case KG -> amount *= factorFromKG;
      case G -> amount *= factorFromG;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    measurement.setValidUnit(targetUnit);
    measurement.setAmount(roundToTwoDecimals(amount));
  }

  private void convertLiquid(Measurement sourceMeasurement, ValidUnit targetUnit, float factorFromL, float factorFromDL, float factorFromML) {
    float amount = sourceMeasurement.getAmount();
    ValidUnit currentUnit = sourceMeasurement.getValidUnit();
    switch (currentUnit) {
      case L -> amount *= factorFromL;
      case DL -> amount *= factorFromDL;
      case ML -> amount *= factorFromML;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    sourceMeasurement.setValidUnit(targetUnit);
    sourceMeasurement.setAmount(roundToTwoDecimals(amount));
  }

  private float roundToTwoDecimals(float value) {
    return Math.round(value * 100) / 100.0f;
  }

}