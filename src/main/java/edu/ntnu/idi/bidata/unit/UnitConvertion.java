package edu.ntnu.idi.bidata.unit;

public class UnitConvertion {


  // Conversion factors
  private static final float KG_TO_GRAMS = 1000.0f;
  private static final float G_TO_GRAMS = 1.0f;
  private static final float ML_TO_LITERS = 0.001f;
  private static final float DL_TO_LITERS = 0.1f;
  private static final float L_TO_LITERS = 1.0f;

  // Convert to grams
  public float toGrams(float amount, String unit) {
    switch (unit.toLowerCase()) {
      case "kg":
        return amount * KG_TO_GRAMS;
      case "g":
        return amount * G_TO_GRAMS;
      default:
        throw new IllegalArgumentException("Unsupported unit for grams: " + unit);
    }
  }

  // Convert to liters
  public float toLiters(float amount, String unit) {
    switch (unit.toLowerCase()) {
      case "ml":
        return amount * ML_TO_LITERS;
      case "dl":
        return amount * DL_TO_LITERS;
      case "l":
        return amount * L_TO_LITERS;
      default:
        throw new IllegalArgumentException("Unsupported unit for liters: " + unit);
    }
  }
}
