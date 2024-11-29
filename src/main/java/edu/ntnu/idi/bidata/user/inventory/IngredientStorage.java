package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.command.Utility;

import java.time.LocalDate;
import java.util.*;

/**
 * The Inventory class manages collections of ingredients
 * stored in various named collections.
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class IngredientStorage {

  private final Map<String, List<Ingredient>> ingredientMap;
  private String storageName;

  /**
   * Constructor for the Storage class.
   */
  public IngredientStorage(String storageName) {
    setStorageName(storageName);
    ingredientMap = new HashMap<>();
  }

  /**
   * Adds a given ingredient to the storage.
   * If the ingredient is already present, it will be merged
   * with the existing one.
   * Otherwise, the new ingredient is saved directly.
   *
   * @param newIngredient The ingredient to be added to the storage.
   */
  public void addIngredient(Ingredient newIngredient) {
    Utility.assertNonNull(newIngredient);
    if (hasMatchingExpiryDate(newIngredient)) {
      mergeIngredient(newIngredient);
    } else {
      addToList(newIngredient);
    }
  }

  /**
   * Removes an ingredient from the storage if it is present and prints a success/failure message.
   *
   * @param name       the name of the ingredient to be removed
   * @param expiryDate the expiry date of the ingredient to be removed
   */
  public boolean removeIngredient(String name, LocalDate expiryDate) {
    List<Ingredient> ingredientList = getIngredientList(name);
    boolean result = ingredientList.remove(findIngredient(name, expiryDate));
    if (ingredientList.isEmpty()) {
      ingredientMap.remove(Utility.createKey(name));
    }
    return result;
  }

  /**
   * Checks if the specified ingredient is present in the storage.
   *
   * @param ingredientName the name of the ingredient to check for
   * @return true if the ingredient is present; false otherwise
   */
  public boolean isIngredientPresent(String ingredientName) {
    return ingredientMap.containsKey(Utility.createKey(ingredientName));
  }

  public List<Ingredient> getIngredientList(String ingredientName) {
    return ingredientMap.get(Utility.createKey(ingredientName));
  }

  public List<Ingredient> getIngredientList(Ingredient ingredient) {
    return ingredientMap.get(Utility.createKey(ingredient));
  }

  public void removeExpired() {
    if (ingredientMap.isEmpty()) {
      System.out.println("No ingredients available to check for expiry.");
      return;
    }

    List<Ingredient> removedIngredients = new ArrayList<>(); // List to track removed ingredients

    for (List<Ingredient> ingredientList : ingredientMap.values()) {
      ingredientList.removeIf(ingredient -> {
        boolean isExpired = ingredient.getExpiryDate().isBefore(LocalDate.now());
        if (isExpired) {
          removedIngredients.add(ingredient); // Collect expired ingredient
        }
        return isExpired;
      });
    }

    if (!removedIngredients.isEmpty()) {
      System.out.println(removedIngredients.size() + " expired ingredients were removed:");
      removedIngredients.forEach(System.out::println);
    } else {
      System.out.println("No expired ingredients were found.");
    }
  }

  /**
   * Retrieves an ingredient matching the specified name and expiry date.
   *
   * @param ingredientName       the name of the ingredient to find
   * @param ingredientExpiryDate the expiry date of the ingredient to match
   * @return the matching Ingredient, or null if no match is found
   */
  public Ingredient findIngredient(String ingredientName, LocalDate ingredientExpiryDate) {
    return ingredientMap.get(ingredientName.toLowerCase()).stream()
        .filter(ingredient -> ingredient.getExpiryDate().isEqual(ingredientExpiryDate))
        .findFirst().orElse(null);
  }

  /**
   * Builds and returns a string representation of the storage.
   * The string begins with the storage name followed by each ingredient's details.
   * If the storage is empty, it indicates so.
   *
   * @return a string representation of the storage and its contents
   */
  public String getStorageString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n ").append(storageName);
    if (ingredientMap.isEmpty()) {
      stringBuilder.append("\n   (Empty)");
    } else {
      ingredientMap.values().stream()
          .flatMap(Collection::stream)
          .map(Ingredient::toString)
          .forEach(string -> stringBuilder.append("\n").append(string));
    }
    return stringBuilder.toString();
  }

  public float getAllValue() {
    return ingredientMap.values().stream()
        .flatMap(Collection::stream)
        .map(Ingredient::getValue)
        .reduce(0.0f, Float::sum);
  }

  public String getStorageName() {
    return storageName;
  }

  /**
   * Sets the name of the storage.
   *
   * @param storageName the name of the storage to set
   */
  private void setStorageName(String storageName) {
    this.storageName = storageName;
  }

  /**
   * Retrieves a list of all expired ingredients from the storage.
   *
   * @return a list of expired ingredients; an empty list if no ingredients are expired.
   */
  public List<Ingredient> getAllExpired() {
    return ingredientMap.values().stream()
        .flatMap(List::stream)
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(LocalDate.now()))
        .toList();
  }

  /**
   * Merges the given ingredient into the existing one in the ingredient map.
   * If the ingredient is already present in the map with the same expiry date,
   * the two ingredients are merged.
   *
   * @param ingredientToMerge The ingredient to be merged with an existing ingredient in the map.
   */
  private void mergeIngredient(Ingredient ingredientToMerge) {
    String name = ingredientToMerge.getName();
    LocalDate expiryDate = ingredientToMerge.getExpiryDate();
    Ingredient existingIngredient = findIngredient(name, expiryDate);
    existingIngredient.merge(ingredientToMerge);
  }

  /**
   * Adds the specified ingredient to the list associated with its map key.
   * If the map key does not already exist, a new list is created.
   *
   * @param ingredientToAdd the ingredient to be added to the list
   */
  private void addToList(Ingredient ingredientToAdd) {
    if (ingredientToAdd != null) {
      ingredientMap.computeIfAbsent(Utility.createKey(ingredientToAdd), key -> new ArrayList<>())
          .add(ingredientToAdd);
    }
  }

  /**
   * Checks if the specified ingredient is present in the storage and if it has the same expiry date.
   *
   * @param ingredientToCheck The ingredient to be checked in the storage.
   * @return true if the ingredient is present and has the same expiry date, false otherwise.
   */
  private boolean isIngredientPresent(Ingredient ingredientToCheck) {
    return ingredientMap.containsKey(Utility.createKey(ingredientToCheck));
  }

  /**
   * Checks if there is any ingredient in the storage with an expiry date matching the given ingredient.
   *
   * @param ingredientToCheck The ingredient whose expiry date is to be checked against stored ingredients.
   * @return true if a matching expiry date is found; false otherwise.
   */
  private boolean hasMatchingExpiryDate(Ingredient ingredientToCheck) {
    List<Ingredient> ingredientList = getIngredientList(ingredientToCheck);
    return ingredientList != null && ingredientList.stream()
        .anyMatch(ingredient -> getIngredientExpiryDate(ingredient).isEqual(getIngredientExpiryDate(ingredientToCheck)));
  }

  /**
   * Retrieves the expiry date of the given ingredient.
   *
   * @param ingredient The ingredient whose expiry date is to be retrieved.
   * @return The expiry date of the ingredient.
   */
  private LocalDate getIngredientExpiryDate(Ingredient ingredient) {
    return ingredient.getExpiryDate();
  }

}
