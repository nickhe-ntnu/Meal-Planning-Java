package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents an ingredient with a name, unit, and amount.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class Ingredient {
  // Instance variables
  private String name;
  private float amount;
  private ValidUnit unit;
  private LocalDate expiryDate;
  private float unitPrice;

  /**
   * Create a new ingredient with a name, unit, and amount.
   *
   * @param name   the name of the ingredient
   * @param unit   the unit of the ingredient
   * @param amount the amount of the ingredient
   * @throws IllegalArgumentException if the name is null or empty, the unit is null or empty, or the
   *                                  amount is negative or NaN
   */
  public Ingredient(String name, float amount, ValidUnit unit, float unitPrice, int daysToExpiry) {
    setName(name);
    setAmount(amount);
    setUnit(unit);
    setUnitPrice(unitPrice);
    setExpiryDate(daysToExpiry);
  }

  public static Ingredient merge(Ingredient firstI, Ingredient secondI) {
    if (isValidToMerge(firstI, secondI)) {
      String mergedName = firstI.name != null ? firstI.name : secondI.name;
      float mergedAmount = firstI.amount + secondI.amount;
      ValidUnit mergedUnit = firstI.unit;
      float mergedPricePerUnit = Math.max(firstI.unitPrice, secondI.unitPrice);
      int mergedExpiryDate = (int) ChronoUnit.DAYS.between(LocalDate.now(), firstI.expiryDate);
      return new Ingredient(mergedName, mergedAmount, mergedUnit, mergedPricePerUnit, mergedExpiryDate);
    } else {
      return null;
    }
  }

  private static boolean isValidToMerge(Ingredient firstI, Ingredient secondI) {
    // Null check first to prevent null pointer exceptions
    if (firstI == null || secondI == null) {
      return false;
    }
    if (firstI.name == null || secondI.name == null) {
      return false;
    }
    if (!firstI.name.equals(secondI.name)) {
      return false;
    }
    if (!firstI.expiryDate.isEqual(secondI.expiryDate)) {
      return false;
    }
    return true;
  }

  public void setUnitPrice(float unitPrice) {
    if (unitPrice < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    } else if (unitPrice > 1000) {
      throw new IllegalArgumentException("Please enter a more reasonable unit price (max 1000");
    }
    this.unitPrice = unitPrice;
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
   * Set the unit of the ingredient.
   *
   * @param unit the unit of the ingredient
   * @throws IllegalArgumentException if the unit is null or empty
   */
  public void setUnit(ValidUnit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null or empty");
    }
    this.unit = unit;
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

  public void setExpiryDate(int daysToExpiry) {
    if (daysToExpiry < 0) {
      throw new IllegalArgumentException("Days to expiry cannot be negative");
    }
    this.expiryDate = LocalDate.now();
    this.expiryDate = expiryDate.plusDays(daysToExpiry);
  }

  public String getName() {
    return name;
  }

  public float getAmount() {
    return amount;
  }

  public ValidUnit getUnit() {
    return unit;
  }

  @Override
  public String toString() {
    int dayTilExpiry = (int) ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    return "\n  * " + name + ": " + amount + " " + unit + " - Best before: " + expiryDate + " (in " + dayTilExpiry + " days)";
  }

}
