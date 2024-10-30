package edu.ntnu.idi.bidata.user.inventory;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The Inventory class manages collections of ingredients
 * stored in various named collections.
 * @author Nick Heggø
 * @version 2024-10-30
 */
public class Inventory {

  private final HashMap<String, HashSet<Ingredient>> storageMap;
  private HashSet<Ingredient> currentStorage;

  /**
   * Constructor for the Storage class.
   */
  public Inventory() {
    storageMap = new HashMap<>();
  }

  /**
   * Checks if a collection is present in the storage.
   *
   * @param storage The name of the storage to check.
   * @return True if the collection is present, false otherwise.
   */
  public boolean isCollectionPresent(String storage) {
    return storageMap.keySet().stream()
        .anyMatch(keyName -> keyName.equalsIgnoreCase(storage));
  }

  /**
   * Adds a new collection to the storage if it is not already present.
   *
   * @param storage The name of the storage to add.
   * @return True if the new collection was added successfully, false otherwise.
   */
  public boolean addNewCollection(String storage) {
    boolean success = false;
    if (!isCollectionPresent(storage)) {
      storageMap.put(storage, new HashSet<>());
      success = true;
    }
    return success;
  }

  /**
   * Remove an ingredient from a collection.
   *
   * @param storage    The name of the storage.
   * @param ingredient The ingredient to remove.
   */
  public void removeIngredient(String storage, Ingredient ingredient) {
    if (!isCollectionPresent(storage)) {
      throw new IllegalArgumentException("Collection does not exist");
    }
    storageMap.get(storage).remove(ingredient);
  }

  /**
   * Check if a collection contains an ingredient.
   *
   * @param storage        The name of the storage.
   * @param ingredientName The name of the ingredient.
   * @return True if the collection contains the ingredient, false otherwise.
   */
  public boolean collectionContainsIngredient(String storage, String ingredientName) {
    if (!isCollectionPresent(storage)) {
      throw new IllegalArgumentException("Collection does not exist");
    }
    return storageMap.get(storage).stream()
        .map(edu.ntnu.idi.bidata.user.inventory.Ingredient::getName)
        .anyMatch(name -> name.equals(ingredientName));
  }

  /**
   * Get an ingredient from a collection.
   *
   * @param ingredientName The name of the ingredient.
   * @return The ingredient if it exists, null otherwise.
   */
  public void getIngredientFromStorage(String ingredientName) {
    storageMap.forEach((storage, ingredients) -> ingredients.stream()
        .filter(ingredient -> ingredient.getName().equals(ingredientName))
        .forEach(ingredient -> System.out.println("Ingredient " + ingredientName + " is in storage: " + storage)));
  }

  /**
   * Print all the ingredients in the storage.
   *
   * @return String of everything storage in the inventory.
   */
  public String getInventoryString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("List of all inventory:");
    storageMap.forEach((storageName, storageSet) -> {
      stringBuilder.append("\n  * ").append(storageName);
      storageSet.forEach(ingredient -> stringBuilder.append("\n    - ").append(ingredient.toString()));
    });
    return stringBuilder.toString();
  }

  /**
   * Get all the ingredients in a collection.
   *
   * @return The ingredients in the collection.
   */
  public HashMap<String, HashSet<Ingredient>> getAllStorage() {
    return storageMap;
  }
}
