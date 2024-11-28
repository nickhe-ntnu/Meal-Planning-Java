package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

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
    return (s == null) ? null : s.toLowerCase();
  }

  /**
   * Generates a lowercase key for the given Ingredient.
   *
   * @param o the Ingredient object
   * @return the lowercase name of the Ingredient, or null if the Ingredient is null.
   */
  public static String createKey(Ingredient o) {
    return (o == null) ? null : o.getName().toLowerCase();
  }
}
