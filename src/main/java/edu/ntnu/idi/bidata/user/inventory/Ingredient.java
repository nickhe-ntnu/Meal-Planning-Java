package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.ValidUnit;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an ingredient with a name, unit and amount.
 *
 * @author Nick Heggø
 * @version 2024-10-31
 */
public class Ingredient {
  // Instance variables
  private String name;
  private int amount;
  private ValidUnit unit;
  private LocalDate expiryDate;
  private int pricePerKilogram;

  /**
   * Create a new ingredient with a name, unit and amount.
   *
   * @param name   the name of the ingredient
   * @param unit   the unit of the ingredient
   * @param amount the amount of the ingredient
   * @throws IllegalArgumentException if the name is null or empty, the unit is null or empty, or the
   *                                  amount is negative or NaN
   */
  public Ingredient(String name, int amount, ValidUnit unit, int pricePerKilogram, int daysToExpiry) {
    setName(name);
    setAmount(amount);
    setUnit(unit);
    setPricePerKilogram(pricePerKilogram);
    setExpiryDate(daysToExpiry);
  }

  public void setPricePerKilogram(int pricePerKilogram) {
    if (pricePerKilogram < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    } else if (pricePerKilogram > 1000) {
      throw new IllegalArgumentException("Please enter a more reasonable price (max 1000");
    }
    this.pricePerKilogram = pricePerKilogram;
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
  public void setAmount(int amount) {
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

  public double getAmount() {
    return amount;
  }

  public ValidUnit getUnit() {
    return unit;
  }

  @Override
  public String toString() {
    return this.getName() + ": " +
        this.getAmount() + " " +
        this.getUnit() + ".";
  }

}
