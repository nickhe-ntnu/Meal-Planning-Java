package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.ValidUnit;

/**
 * Represents an ingredient with a name, unit and amount.
 * @author Nick Heggø
 * @version 2024-10-30
 */
public class Ingredient {
  // Instance variables
  private String name;
  private int amount;
  private ValidUnit unit;

  /**
   * Create a new ingredient with a name, unit and amount.
   *
   * @param name   the name of the ingredient
   * @param unit   the unit of the ingredient
   * @param amount the amount of the ingredient
   * @throws IllegalArgumentException if the name is null or empty, the unit is null or empty, or the
   *                                  amount is negative or NaN
   */
  public Ingredient(String name, int amount, ValidUnit unit) {
    setName(name);
    setAmount(amount);
    setUnit(unit);
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
    } else {
      this.unit = unit;
    }
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
    } else {
      this.amount = amount;
    }
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
