package edu.ntnu.idi.bidata.user.inventory;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a storage of ingredients.
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
   * Check if a collection exists.
   *
   * @param storage The name of the storage.
   * @return True if the collection exists, false otherwise.
   */
  public boolean collectionExists(String storage) {
    return storageMap.keySet().stream()
        .anyMatch(keyName -> keyName.equalsIgnoreCase(storage));
  }

  /**
   * Create a new collection.
   *
   * @param storage The name of the storage.
   */
  public boolean addStorage(String storage) {
    boolean success = false;
    if (!collectionExists(storage)) {
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
    if (!collectionExists(storage)) {
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
    if (!collectionExists(storage)) {
      throw new IllegalArgumentException("Collection does not exist");
    }
    return storageMap.get(storage).stream()
        .map(edu.ntnu.idi.bidata.user.inventory.Ingredient::getName)
        .anyMatch(name -> name.equals(ingredientName));
  }

  /**
   * Get an ingredient from a collection.
   *
   * @param storage        The name of the storage.
   * @param ingredientName The name of the ingredient.
   * @return The ingredient if it exists, null otherwise.
   */
  public void getIngredientFromStorage(String ingredientName) {
    storageMap.forEach((storage, ingredients) -> ingredients.stream()
        .filter(ingredient -> ingredient.getName().equals(ingredientName))
        .forEach(ingredient -> System.out.println("Ingredient " + ingredientName + " is in storage: " + storage)));
  }

  /**
   * Get all the ingredients in a collection.
   *
   * @param storage The name of the storage.
   * @return The ingredients in the collection.
   */
  public HashMap<String, HashSet<Ingredient>> getAllStorage() {
    return storageMap;
  }

  /**
   * Print all the ingredients in the storage.
   * @return String of everything storage in the inventory.
   */
  public String getInventoryString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("List of all inventory:");
    storageMap.forEach((storageName, storageSet) -> {
      stringBuilder.append("\n  * " + storageName);
      storageSet.forEach(ingredient -> stringBuilder.append("\n    - ")
          .append(ingredient.getName()).append(" ")
          .append(ingredient.getAmount()).append(" ")
          .append(ingredient.getUnit()));
    });
    return stringBuilder.toString();
  }

}
