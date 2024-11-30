package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

/**
 * A utility class providing methods to manipulate strings and objects.
 *
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class Utility {

  private Utility() {
  }

  /**
   * Converts the input string to lowercase if it is not null.
   *
   * @param s the input string to be converted.
   * @return the lowercase version of the input string if not null, otherwise null.
   */
  public static String createKey(String s) {
    return (s == null) ? null : s.trim().toLowerCase();
  }

  /**
   * Generates a lowercase key for the given Ingredient.
   *
   * @param o the Ingredient object
   * @return the lowercase name of the Ingredient, or null if the Ingredient is null.
   */
  public static String createKey(Ingredient o) {
    return (o == null) ? null : o.getName().trim().toLowerCase();
  }

  public static void assertNoneNull(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Null is prohibited.");
    }
  }

  public static void assertNoneBlank(String o) {
    if (o.isBlank()) {
      throw new IllegalArgumentException("String cannot be blank.");
    }
  }

  public static String getOrdinalSuffix(int number) {
    if (number % 100 >= 11 && number % 100 <= 13) {
      return "th";
    }
    return switch (number % 10) {
      case 1 -> "st";
      case 2 -> "nd";
      case 3 -> "rd";
      default -> "th";
    };
  }
}
