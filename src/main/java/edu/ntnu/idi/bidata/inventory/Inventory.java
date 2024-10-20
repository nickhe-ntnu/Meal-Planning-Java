package edu.ntnu.idi.bidata.inventory;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a storage of ingredients.
 */
public class Inventory {

  private HashMap<String, HashSet<Ingredient>> namedStorages;

  /**
   * Constructor for the Storage class.
   */
  public Inventory() {
    namedStorages = new HashMap<>();
  }

  /**
   * Check if a collection exists.
   *
   * @param storage The name of the storage.
   * @return True if the collection exists, false otherwise.
   */
  public boolean collectionExists(String storage) {
    return namedStorages.keySet().stream()
        .anyMatch(keyName -> keyName.equalsIgnoreCase(storage));
  }

  /**
   * Create a new collection.
   *
   * @param storage The name of the storage.
   */
  public void addStorage(String storage) {
    if (collectionExists(storage)) {
      throw new IllegalArgumentException("Storage already exists");
    }
    namedStorages.put(storage, new HashSet<>());
  }

  /**
   * Add an ingredient to a collection.
   *
   * @param storage    The name of the storage.
   * @param ingredient The ingredient to add.
   */
  public void addIngredient() {
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
    namedStorages.get(storage).remove(ingredient);
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
    return namedStorages.get(storage).stream()
        .map(Ingredient::getName)
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
    namedStorages.forEach((storage, ingredients) -> ingredients.stream()
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
    return namedStorages;
  }

  /**
   * Print all the ingredients in the storage.
   */
  public void printInventory() {
    namedStorages.forEach((nameOfStorage, storageList) -> {
      System.out.println("  * " + nameOfStorage);
      storageList.forEach(ingredient -> System.out.println("    - " + ingredient.getName() + " "
          + ingredient.getAmount() + " " + ingredient.getUnit()));
    });
  }

}
