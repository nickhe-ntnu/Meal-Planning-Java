package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

/**
 * Class for converting between different units of measurement.
 * The UnitConverter class provides methods to convert values from one
 * unit of measurement to another, using the defined ValidUnit
 * enumeration which includes units for weight (KG, G) and volume (L, DL, ML).
 *
 * @author Nick Heggø
 * @version 2024-11-08
 */
public class UnitConverter {

  /**
   * Default constructor for the UnitConverter class.
   */
  public UnitConverter() {
  }

  /**
   * Converts the given ingredient to its standard unit of measurement.
   * For solid ingredients, the standard unit is kilograms (KG).
   * For liquid ingredients, the standard unit is liters (L).
   *
   * @param ingredientToBeConverted the ingredient to be converted to its standard unit
   */
  public void convertToStandard(Ingredient ingredientToBeConverted) {
    String ingredientType = "";
    if (ingredientToBeConverted != null) {
      ingredientType = ingredientToBeConverted.getIngredientTypeString();
    }
    if (ingredientType.equals("SOLID")) {
      convertToKG(ingredientToBeConverted);
    } else if (ingredientType.equals("LIQUID")) {
      convertToLiter(ingredientToBeConverted);
    }
  }

  /**
   * Converts the given ingredient to grams (G).
   *
   * @param ingredientToBeConverted the ingredient to be converted to grams
   */
  public void convertToGrams(Ingredient ingredientToBeConverted) {
    convertSolid(ingredientToBeConverted, ValidUnit.G, 1000, 1);
  }

  /**
   * Converts the given ingredient to kilograms (KG).
   *
   * @param ingredientToBeConverted the ingredient to be converted to kilograms
   */
  public void convertToKG(Ingredient ingredientToBeConverted) {
    convertSolid(ingredientToBeConverted, ValidUnit.KG, 1, 0.001f);
  }

  /**
   * Converts the given ingredient to liters (L).
   *
   * @param ingredientToBeConverted the ingredient to be converted to liters
   */
  public void convertToLiter(Ingredient ingredientToBeConverted) {
    convertLiquid(ingredientToBeConverted, ValidUnit.L, 1, 0.1f, 0.001f);
  }

  /**
   * Converts the given ingredient to deciliters (DL).
   *
   * @param ingredientToBeConverted the ingredient to be converted to deciliters
   */
  public void convertToDeciLiter(Ingredient ingredientToBeConverted) {
    convertLiquid(ingredientToBeConverted, ValidUnit.DL, 10, 1f, 0.01f);
  }

  /**
   * Converts the given ingredient to milliliters (ML).
   *
   * @param ingredientToBeConverted the ingredient to be converted to milliliters
   */
  public void convertToMilliLiter(Ingredient ingredientToBeConverted) {
    convertLiquid(ingredientToBeConverted, ValidUnit.ML, 1000, 100f, 1f);
  }

  public void autoMergeUnit(ValidUnit targetUnit, Ingredient ingredientToBeConverted) {
    if (targetUnit != null && ingredientToBeConverted.getValidUnit() != null) {
      switch (targetUnit) {
        case L -> convertToLiter(ingredientToBeConverted);
        case DL -> convertToDeciLiter(ingredientToBeConverted);
        case ML -> convertToMilliLiter(ingredientToBeConverted);
        case KG -> convertToKG(ingredientToBeConverted);
        case G -> convertToGrams(ingredientToBeConverted);
        default ->
            throw new IllegalArgumentException("Illegal operation: cannot convert from " + ingredientToBeConverted.getValidUnit() + " to " + targetUnit);
      }
    }
  }

  /**
   * Converts the given ingredient to the specified target unit.
   * The conversion is based on provided factors for kilograms (KG) and grams (G).
   *
   * @param ingredient   The ingredient to be converted.
   * @param targetUnit   The unit to which the ingredient should be converted.
   * @param factorFromKG The conversion factor to be applied if the current unit is kilograms.
   * @param factorFromG  The conversion factor to be applied if the current unit is grams.
   */
  private void convertSolid(Ingredient ingredient, ValidUnit targetUnit, float factorFromKG, float factorFromG) {
    float amount = ingredient.getAmount();
    ValidUnit currentUnit = ingredient.getValidUnit();
    switch (currentUnit) {
      case KG -> amount *= factorFromKG;
      case G -> amount *= factorFromG;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    ingredient.setValidUnit(targetUnit);
    ingredient.setAmount(roundToTwoDecimals(amount));
  }

  /**
   * Converts the given ingredient's amount to the specified target unit.
   * The conversion is based on provided factors for liters (L), deciliters (DL), and milliliters (ML).
   *
   * @param ingredientToBeConverted the ingredient to be converted
   * @param targetUnit              the unit to which the ingredient should be converted
   * @param factorFromL             the conversion factor to be applied if the current unit is liters
   * @param factorFromDL            the conversion factor to be applied if the current unit is deciliters
   * @param factorFromML            the conversion factor to be applied if the current unit is milliliters
   */
  private void convertLiquid(Ingredient ingredientToBeConverted, ValidUnit targetUnit, float factorFromL, float factorFromDL, float factorFromML) {
    float amount = ingredientToBeConverted.getAmount();
    ValidUnit currentUnit = ingredientToBeConverted.getValidUnit();
    switch (currentUnit) {
      case L -> amount *= factorFromL;
      case DL -> amount *= factorFromDL;
      case ML -> amount *= factorFromML;
      default ->
          throw new IllegalArgumentException("Illegal operation: convert " + currentUnit + " to " + targetUnit + ".");
    }
    ingredientToBeConverted.setValidUnit(targetUnit);
    ingredientToBeConverted.setAmount(roundToTwoDecimals(amount));
  }

  /**
   * Rounds the given float value to two decimal places.
   *
   * @param value the float value to be rounded
   * @return the rounded value to two decimal places
   */
  private float roundToTwoDecimals(float value) {
    return Math.round(value * 100) / 100.0f;
  }

}