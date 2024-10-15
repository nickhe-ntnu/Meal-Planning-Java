package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.ingredient.Ingredient;

import java.util.Scanner;

/**
 * The main starting point of your application. Let this class create the
 * instance of your main-class that starts your application.
 */
public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean isRunning = true;
    while (isRunning) {
      try {
        // Print menu
        System.out.println("1. Add ingredient");
        System.out.println("2. Remove ingredient");
        System.out.println("3. List ingredients");
        System.out.println("4. Exit");
        System.out.println("Enter your choice: ");
        Integer choice = Integer.valueOf(scanner.nextLine());
        // Handle user input
        switch (choice) {
          // Add ingredient
          case 1 -> {
            System.out.println("Enter ingredient name: ");
            String name = scanner.nextLine();
            System.out.println("Enter ingredient unit: ");
            String unit = scanner.nextLine();
            System.out.println("Enter ingredient amount: ");
            float amount = Float.valueOf(scanner.nextLine());
            Ingredient newIngredient = new Ingredient(name, unit, amount);
            System.out.println("Added ingredient: " + newIngredient.toString());
          }
          // Remove ingredient
          case 2 -> {
            System.out.println("Enter ingredient name to remove: ");
            String nameToRemove = scanner.nextLine();
            System.out.println("Enter ingredient unit to remove: ");
            String unitToRemove = scanner.nextLine();
            System.out.println("Enter ingredient amount to remove: ");
            float amountToRemove = Float.valueOf(scanner.nextLine());
            Ingredient ingredientToRemove = new Ingredient(nameToRemove, unitToRemove, amountToRemove);
            System.out.println("Removed ingredient: " + ingredientToRemove.toString());
          }
          // List ingredients
          case 3 -> System.out.println("Listing ingredients: ");
          // Exit
          case 4 -> isRunning = false;
          default -> System.out.println("Invalid choice, please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
