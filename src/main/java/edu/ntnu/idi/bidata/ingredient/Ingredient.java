package edu.ntnu.idi.bidata.ingredient;

/**
 * Represents an ingredient with a name, unit and amount.
 */
public class Ingredient {
  // Instance variables
  private String name;
  private String unit;
  private float amount;

  /**
   * Create a new ingredient with a name, unit and amount.
   *
   * @param name   the name of the ingredient
   * @param unit   the unit of the ingredient
   * @param amount the amount of the ingredient
   * @throws IllegalArgumentException if the name is null or empty, the unit is null or empty, or the
   *                                  amount is negative or NaN
   */
  public Ingredient(String name, String unit, float amount) {
    setName(name);
    setUnit(unit);
    setAmount(amount);
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
  public void setUnit(String unit) {
    if (unit == null || unit.isBlank()) {
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
    if (amount < 0 || Float.isNaN(amount)) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "Ingredient{" +
        "name='" + name + '\'' + ", " +
        "unit='" + unit + '\'' + ", " +
        "amount=" + amount + '}';
  }

  public static void main(String[] args) {
    Ingredient ingredient = new Ingredient("Sugar", "g", 100);
    System.out.println(ingredient.toString());
  }

}
