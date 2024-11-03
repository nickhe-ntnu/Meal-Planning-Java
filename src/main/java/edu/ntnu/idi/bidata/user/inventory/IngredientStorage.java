package edu.ntnu.idi.bidata.user.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * The Inventory class manages collections of ingredients
 * stored in various named collections.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class IngredientStorage {

  private String storageName;
  private final Map<String, Ingredient> ingredientMap;

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
   * @param ingredientToAdd The ingredient to be added to the storage.
   */
  public void addIngredient(Ingredient ingredientToAdd) {
    String ingredientName = ingredientToAdd.getName();
    if (isIngredientPresent(ingredientName)) {
      mergeIngredient(ingredientToAdd);
    } else {
      saveIngredient(ingredientToAdd);
    }
  }

  /**
   * Removes an ingredient from the storage if it is present.
   *
   * @param ingredientName the name of the ingredient to be removed
   * @return true if the ingredient was present and removed, false otherwise
   */
  public boolean removeIngredient(String ingredientName) {
    boolean success = isIngredientPresent(ingredientName);
    if (success) {
      removeFromMap(ingredientName);
    }
    return success;
  }

  /**
   * Print all the ingredients in the storage.
   *
   * @return String of everything storage in the inventory.
   */
  public String getStorageString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n# ").append(storageName);
    ingredientMap.values().forEach(ingredient -> stringBuilder.append(ingredient.toString()));
    return stringBuilder.toString();
  }

  /**
   * Merges the given ingredient with the existing one in the storage if present.
   *
   * @param ingredientToMerge The ingredient to be merged with an existing one in the storage.
   */
  private void mergeIngredient(Ingredient ingredientToMerge) {
    Ingredient existingIngredient = ingredientMap.get(ingredientToMerge.getName().toLowerCase());
    Ingredient mergedIngredient = Ingredient.merge(existingIngredient, ingredientToMerge);
    if (mergedIngredient != null) {
      saveIngredient(mergedIngredient);
    }
  }

  /**
   * Saves the given ingredient to the storage.
   * The ingredient is converted to lowercase before
   * being added to the storage map.
   *
   * @param ingredientToPut the ingredient to be saved to the storage
   */
  private void saveIngredient(Ingredient ingredientToPut) {
    ingredientMap.put(ingredientToPut.getName().toLowerCase(), ingredientToPut);
  }

  /**
   * Removes the specified key from the ingredient map after converting it to lowercase.
   *
   * @param key the key to be removed from the ingredient map
   */
  private void removeFromMap(String key) {
    ingredientMap.remove(key.toLowerCase());
  }

  /**
   * Checks if a given ingredient is present in the storage.
   *
   * @param ingredientName the name of the ingredient to check for
   * @return true if the ingredient is present, false otherwise
   */
  private boolean isIngredientPresent(String ingredientName) {
    return ingredientMap.containsKey(ingredientName.toLowerCase());
  }

  /**
   * Sets the name of the storage.
   *
   * @param storageName the name of the storage to set
   */
  private void setStorageName(String storageName) {
    this.storageName = storageName;
  }

  public String getStorageName() {
    return storageName;
  }
}
