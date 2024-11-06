package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Represents an ingredient with a name, unit, and amount.
 *
 * @author Nick Heggø
 * @version 2024-11-07
 */
public class Ingredient {
  // Instance variables
  private String name;
  private float amount;
  private ValidUnit validUnit;
  private LocalDate expiryDate;
  private float unitPrice;
  private IngredientType ingredientType;

  /**
   * Create a new ingredient with a name, unit, and amount.
   *
   * @param name      the name of the ingredient
   * @param validUnit the unit of the ingredient
   * @param amount    the amount of the ingredient
   * @throws IllegalArgumentException if the name is null or empty, the unit is null or empty, or the
   *                                  amount is negative or NaN
   */
  public Ingredient(String name, float amount, ValidUnit validUnit, float unitPrice, int daysToExpiry) {
    setName(name);
    setAmount(amount);
    setValidUnit(validUnit);
    setUnitPrice(unitPrice);
    setExpiryDate(daysToExpiry);
  }

  /**
   * Returns a string representation of the ingredient, including its name, amount, unit, and expiry date.
   *
   * @return a string containing the ingredient's details and the number of days until its expiry.
   */
  @Override
  public String toString() {
    int dayTilExpiry = getDaysBetween(this.getExpiryDate());
    return "\n  * " + name + ": " + amount + " " + validUnit + " - Best before: " + expiryDate + " (in " + dayTilExpiry + " days)";
  }

  /**
   * Merges the specified ingredient with the current ingredient if they are valid to merge.
   * The name and expiry date will not be changed since its already been validated.
   * Unit will also remain unchanged, since the incoming will be
   * converted to match the existing unit been used.
   *
   * @param ingredientToMerge the ingredient to be merged with the current ingredient
   * @return true if the ingredients were successfully merged, false otherwise
   */
  public boolean merge(Ingredient ingredientToMerge) {
    boolean isValidToMerge = isValidToMerge(ingredientToMerge);
    if (isValidToMerge) {
      float mergedAmount = this.getAmount() + ingredientToMerge.getAmount();
      float mergedPricePerUnit = Math.max(this.getUnitPrice(), ingredientToMerge.getUnitPrice());
      this.setAmount(mergedAmount);
      this.setUnitPrice(mergedPricePerUnit);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Calculate the number of days between the current date and a specified future date.
   *
   * @param untilDate the future date until which the number of days is to be calculated
   * @return the number of days between the current date and the specified future date
   * @throws IllegalArgumentException if the untilDate is null
   */
  public int getDaysBetween(LocalDate untilDate) {
    return (int) ChronoUnit.DAYS.between(LocalDate.now(), untilDate);
  }

  /**
   * Checks whether the specified ingredient is valid to merge with the current ingredient.
   *
   * @param ingredientToMerge the ingredient to check for merging compatibility
   * @return true if the ingredient is valid to merge, false otherwise
   */
  private boolean isValidToMerge(Ingredient ingredientToMerge) {
    // Null check first to prevent null pointer exceptions
    boolean isValidToMerge = ingredientToMerge != null;
    if (ingredientToMerge.name == null) {
      isValidToMerge = false;
    }
    if (!this.name.equals(ingredientToMerge.name)) {
      isValidToMerge = false;
    }
    if (!this.expiryDate.isEqual(ingredientToMerge.expiryDate)) {
      isValidToMerge = false;
    }
    return isValidToMerge;
  }

  private IngredientType getIngredientType(ValidUnit validUnit) {
    ValidUnit[] validSolidUnits;
    ValidUnit[] validLiquidUnits;
    validSolidUnits = new ValidUnit[]{ValidUnit.G, ValidUnit.KG};
    validLiquidUnits = new ValidUnit[]{ValidUnit.ML, ValidUnit.DL, ValidUnit.L};
    if (Arrays.stream(validLiquidUnits).anyMatch(unit -> unit == validUnit)) {
      return IngredientType.LIQUID;
    } else if (Arrays.stream(validSolidUnits).anyMatch(unit -> unit == validUnit)) {
      return IngredientType.SOLID;
    }
    return null;
  }

  private void setIngredientType(IngredientType ingredientType) {
    if (ingredientType == null) {
      throw new IllegalArgumentException("Ingredient type cannot be null.");
    }
    this.ingredientType = ingredientType;
  }

  public String getIngredientTypeString() {
    return ingredientType.name();
  }

  /**
   * Retrieves the expiry date of the ingredient.
   * If the expiry date is not set, it returns a date 20 days before the current date.
   *
   * @return the expiry date of the ingredient, or a date 20 days before the current date if not set
   */
  public LocalDate getExpiryDate() {
    return expiryDate != null ? expiryDate : LocalDate.now().minusDays(20);
  }

  /**
   * Sets the expiry date of the ingredient based on the number of days from the current date.
   *
   * @param daysToExpiry the number of days from today until the ingredient expires
   * @throws IllegalArgumentException if the number of days to expiry is negative
   */
  public void setExpiryDate(int daysToExpiry) {
    if (daysToExpiry < 0) {
      throw new IllegalArgumentException("Days to expiry cannot be negative");
    }
    this.expiryDate = LocalDate.now().plusDays(daysToExpiry);
  }

  /**
   * Retrieves the name of the ingredient.
   *
   * @return the name of the ingredient if it is set, otherwise returns an empty string
   */
  public String getName() {
    return name != null ? name : "";
  }

  /**
   * Set the name of the ingredient.
   *
   * @param name the name of the ingredient
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = name;
  }

  /**
   * Retrieves the amount of the ingredient.
   *
   * @return the amount of the ingredient
   */
  public float getAmount() {
    return amount;
  }

  /**
   * Set the amount of the ingredient.
   *
   * @param amount the amount of the ingredient
   * @throws IllegalArgumentException if the amount is negative or NaN
   */
  public void setAmount(float amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    this.amount = amount;
  }

  /**
   * Retrieve the unit of the ingredient.
   *
   * @return the unit of the ingredient if it is set,
   * otherwise returns {@code ValidUnit.UNKNOWN}
   */
  public ValidUnit getValidUnit() {
    return validUnit != null ? validUnit : ValidUnit.UNKNOWN;
  }

  /**
   * Sets the unit of the ingredient.
   *
   * @param validUnit the unit to be set for the ingredient
   * @throws IllegalArgumentException if the unit is null or if the unit is
   *                                  {@code ValidUnit.UNKNOWN}
   */
  public void setValidUnit(ValidUnit validUnit) {
    if (validUnit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    if (validUnit == ValidUnit.UNKNOWN) {
      throw new IllegalArgumentException("Invalid unit, please try again.");
    }
    setIngredientType(getIngredientType(validUnit));
    this.validUnit = validUnit;
  }

  /**
   * Retrieves the unit price of the ingredient.
   *
   * @return the unit price of the ingredient
   */
  public float getUnitPrice() {
    return unitPrice;
  }

  /**
   * Sets the unit price of the ingredient.
   *
   * @param unitPrice the unit price of the ingredient
   * @throws IllegalArgumentException if the unit price is negative or exceeds 1000
   */
  public void setUnitPrice(float unitPrice) {
    if (unitPrice < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    } else if (unitPrice > 1000) {
      throw new IllegalArgumentException("Please enter a more reasonable unit price (max 1000");
    }
    this.unitPrice = unitPrice;
  }

  enum IngredientType {
    SOLID,
    LIQUID
  }

}
