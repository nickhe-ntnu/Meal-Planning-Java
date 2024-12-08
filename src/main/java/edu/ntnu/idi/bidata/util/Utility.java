package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;

import java.util.Random;

/**
 * A utility class providing methods to manipulate strings and objects.
 *
 * @author Nick Heggø
 * @version 2024-12-08
 */
public class Utility {
  private static Random random;

  private Utility() {
  }

  /**
   * Converts the input string to lowercase if it is not null.
   *
   * @param s the input string to be converted.
   * @return the lowercase version of the input string if not null, otherwise null.
   */
  public static String createKey(String s) {
    return (s == null) ? null : s.strip().toLowerCase();
  }

  /**
   * Generates a lowercase key for the given Ingredient.
   *
   * @param o the Ingredient object
   * @return the lowercase name of the Ingredient, or null if the Ingredient is null.
   */
  public static String createKey(Ingredient o) {
    return (o == null) ? null : o.getName().strip().toLowerCase();
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

  public static String capitalizeEachWord(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }

    String[] words = input.split("\\s+");  // Splitting on spaces
    StringBuilder capitalizedSentence = new StringBuilder();

    for (String word : words) {
      if (!word.isEmpty()) {
        capitalizedSentence.append(Character.toUpperCase(word.charAt(0)))
            .append(word.substring(1))
            .append(" ");
      }
    }

    return capitalizedSentence.toString().strip();  // Trim the trailing space
  }

  /**
   * Determines if the user wishes to continue based on the input string.
   *
   * @param input the input string from the user indicating their choice
   * @return true if the input suggests continuation ('yes', 'y' or 'ja'), false otherwise
   */
  public static boolean isInputYes(String input) {
    return switch (input) {
      case String s when s.toLowerCase().contains("yes") -> true;
      case String s when s.toLowerCase().contains("y") -> true;
      case String s when s.toLowerCase().contains("ja") -> true;
      case null, default -> false;
    };
  }

  /**
   * Returns a singleton instance of the Random class.
   *
   * @return a singleton Random object.
   */
  public static Random getInstanceOfRandom() {
    if (random == null) {
      random = new Random();
    }
    return random;
  }
}
