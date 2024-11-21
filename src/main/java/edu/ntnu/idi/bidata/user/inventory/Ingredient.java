package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.UnitConverter;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents an ingredient with a name, unit, and amount.
 *
 * @author Nick Heggø
 * @version 2024-11-08
 */
public class Ingredient {
  // Instance variables
  private String name;
  private float amount;
  private ValidUnit validUnit;
  private LocalDate expiryDate;
  private float standardUnitPrice;
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
  public Ingredient(String name, float amount, ValidUnit validUnit, float standardUnitPrice, int daysToExpiry) {
    setName(name);
    setAmount(amount);
    setValidUnit(validUnit);
    setStandardUnitPrice(standardUnitPrice);
    setExpiryDate(daysToExpiry);
  }

  /**
   * For the sake of demonstrating out of date scenario.
   * TODO remove this backdoor constructor before production
   */
  public Ingredient(String password) {
    if (password.equals("expiredDemo")) {
      Random random = new Random();
      String[] names = new String[]{"Expired Milk", "Expired Chicken", "Expired Egg", "Moldy Bread"};
      name = names[random.nextInt(names.length)];
      amount = random.nextInt(1, 4);
      if (name.equals("Expired Milk")) {
        validUnit = ValidUnit.L;
      } else {
        validUnit = ValidUnit.KG;
      }
      expiryDate = LocalDate.now().minusDays(random.nextInt(4, 17));
    }
  }

  /**
   * Returns a string representation of the ingredient, including its name, amount, unit, and expiry date.
   *
   * @return a string containing the ingredient's details and the number of days until its expiry.
   */
  @Override
  public String toString() {
    String stringToReturn;
    if (LocalDate.now().isAfter(expiryDate)) {
      stringToReturn = getExpiredIngredientString();
    } else {
      int dayTilExpiry = getDaysBetween(this.getExpiryDate());
      stringToReturn = "  * " + name + ": " + amount + " " + validUnit + " - Best before: " + expiryDate + " (in " + dayTilExpiry + " days)";
    }
    return stringToReturn;
  }

  public String getExpiredIngredientString() {
    int daysExpired = Math.abs(getDaysBetween(this.getExpiryDate()));
    return "  * " + name + ": " + amount + " " + validUnit + " - Best before: " + expiryDate + " (Expired " + daysExpired + " days ago)";
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
    return (expiryDate != null) ? expiryDate : LocalDate.now().minusDays(999);
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
    return (name != null) ? name : "";
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
    return (validUnit != null) ? validUnit : ValidUnit.UNKNOWN;
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
  public float getStandardUnitPrice() {
    return standardUnitPrice;
  }

  /**
   * Sets the unit price of the ingredient.
   *
   * @param standardUnitPrice the unit price of the ingredient
   * @throws IllegalArgumentException if the unit price is negative or exceeds 1000
   */
  public void setStandardUnitPrice(float standardUnitPrice) {
    if (standardUnitPrice < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    } else if (standardUnitPrice > 1000) {
      throw new IllegalArgumentException("Please enter a more reasonable unit price (max 1000");
    }
    this.standardUnitPrice = standardUnitPrice;
  }

  /**
   * Sets the type of the ingredient.
   *
   * @param ingredientType the type of the ingredient to set
   * @throws IllegalArgumentException if the ingredient type is null
   */
  private void setIngredientType(IngredientType ingredientType) {
    if (ingredientType == null) {
      throw new IllegalArgumentException("Ingredient type cannot be null.");
    }
    this.ingredientType = ingredientType;
  }

  /**
   * Merges the specified ingredient into this ingredient by combining their amounts and updating the price per unit.
   * If the combined amount is greater than or equal to 1000, this ingredient is converted to a standard unit.
   *
   * @param ingredientToMerge the ingredient to be merged with this ingredient
   */
  public void merge(Ingredient ingredientToMerge) {
    UnitConverter unitConverter = new UnitConverter();
    unitConverter.autoMergeUnit(this.validUnit, ingredientToMerge);
    float mergedAmount = this.getAmount() + ingredientToMerge.getAmount();
    float mergedPricePerUnit = Math.max(this.getStandardUnitPrice(), ingredientToMerge.getStandardUnitPrice());
    this.setAmount(mergedAmount);
    this.setStandardUnitPrice(mergedPricePerUnit);
    if (mergedAmount >= 1000) {
      unitConverter.convertToStandard(this);
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
    return isNonNull(ingredientToMerge) &&
        hasSameName(ingredientToMerge) &&
        hasSameExpiryDate(ingredientToMerge);
  }

  /**
   * Checks if the given ingredient and its name are not null.
   *
   * @param ingredient the ingredient to be checked
   * @return true if the ingredient and its name are not null, false otherwise
   */
  private boolean isNonNull(Ingredient ingredient) {
    return ingredient != null && ingredient.name != null;
  }

  /**
   * Checks if the name of the current ingredient matches the name of the specified ingredient.
   *
   * @param ingredientToMerge the ingredient to compare with the current ingredient
   * @return true if both ingredients have the same name, false otherwise
   */
  private boolean hasSameName(Ingredient ingredientToMerge) {
    return this.name.equals(ingredientToMerge.name);
  }

  /**
   * Checks if the expiry date of the current ingredient is the same as the expiry date of the specified ingredient.
   *
   * @param ingredientToMerge the ingredient to compare expiry dates with
   * @return true if both ingredients have the same expiry date, false otherwise
   */
  private boolean hasSameExpiryDate(Ingredient ingredientToMerge) {
    return this.expiryDate.isEqual(ingredientToMerge.expiryDate);
  }

  /**
   * Determines the type of ingredient based on the provided valid unit.
   *
   * @param validUnit the unit to evaluate
   * @return the corresponding ingredient type if the unit matches, otherwise null
   */
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

  /**
   * Enumeration representing the type of ingredient, which can be
   * either SOLID or LIQUID.
   */
  enum IngredientType {
    SOLID,
    LIQUID
  }

}
