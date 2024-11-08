package edu.ntnu.idi.bidata.user.inventory;

import java.time.LocalDate;
import java.util.*;

/**
 * The Inventory class manages collections of ingredients
 * stored in various named collections.
 *
 * @author Nick Heggø
 * @version 2024-11-08
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
    List<Ingredient> listOfExpired = new ArrayList<>();
    ingredientMap.values().forEach(ingredients -> ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isAfter(LocalDate.now()))
        .forEach(listOfExpired::add));
    return listOfExpired;
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
    if (isIngredientPresent(newIngredient)) {
      if (isIngredientExpiryMatching(newIngredient)) {
        mergeIngredient(newIngredient);
      } else {
        addToList(newIngredient);
      }
    } else {
      addToList(newIngredient);
    }
  }

  /**
   * Removes an ingredient from the storage if it is present.
   *
   * @param ingredientName the name of the ingredient to be removed
   * @return true if the ingredient was present and removed, false otherwise
   */
  public void removeIngredient(String ingredientName, LocalDate expiryDate) {
    if (removeFromStorage(ingredientName, expiryDate)) {
      System.out.println("Successfully removed " + ingredientName + " from " + getStorageName());
    } else {
      System.out.println("Failed to remove " + ingredientName + " from " + getStorageName());
    }

  }

  /**
   * Merges the given ingredient into the existing one in the ingredient map.
   * If the ingredient is already present in the map with the same expiry date,
   * the two ingredients are merged.
   *
   * @param ingredientToMerge The ingredient to be merged with an existing ingredient in the map.
   */
  private void mergeIngredient(Ingredient ingredientToMerge) {
    String mapKey = generateMapKey(ingredientToMerge);
    LocalDate expiryDate = ingredientToMerge.getExpiryDate();
    List<Ingredient> ingredientList = ingredientMap.get(mapKey);
    Optional<Ingredient> existingIngredient = ingredientList.stream().filter(ingredient -> ingredient.getExpiryDate().isEqual(expiryDate)).findFirst();
    existingIngredient.ifPresent(ingredient -> ingredient.merge(ingredientToMerge));
  }

  /**
   * Puts the specified ingredient into the ingredient map.
   * If the ingredient is already present, it will be added to the existing list,
   * otherwise a new list will be created and the ingredient added to it.
   *
   * @param ingredientToAdd The ingredient to be added to the map.
   */
  private void addToList(Ingredient ingredientToAdd) {
    String mapKey = generateMapKey(ingredientToAdd);
    if (isIngredientPresent(ingredientToAdd)) {
      ingredientMap.get(mapKey).add(ingredientToAdd);
    }
    ingredientMap.computeIfAbsent(generateMapKey(ingredientToAdd), key -> new ArrayList<>()).add(ingredientToAdd);
  }

  /**
   * Removes the specified key from the ingredient map after converting it to lowercase.
   *
   * @param key the key to be removed from the ingredient map
   */
  private boolean removeFromStorage(String key, LocalDate expiryDate) {
    List<Ingredient> ingredientList = ingredientMap.get(key.toLowerCase());
    return ingredientList.remove(getMatchingIngredient(key, expiryDate));
  }

  private Ingredient getMatchingIngredient(String ingredientName, LocalDate expiryDate) {
    Optional<Ingredient> matchingIngredient;
    matchingIngredient = ingredientMap.get(ingredientName.toLowerCase()).stream()
        .filter(ingredient -> ingredient.getExpiryDate().isEqual(expiryDate))
        .findFirst();
    return matchingIngredient.orElse(null);
  }

  /**
   * Checks if the specified ingredient is present in the storage and if it has the same expiry date.
   *
   * @param ingredientToCheck The ingredient to be checked in the storage.
   * @return true if the ingredient is present and has the same expiry date, false otherwise.
   */
  private boolean isIngredientPresent(Ingredient ingredientToCheck) {
    String mapKey = generateMapKey(ingredientToCheck);
    return ingredientMap.containsKey(mapKey);
  }

  /**
   * Generates a key for the ingredient map by converting the ingredient's name to lowercase.
   *
   * @param ingredient the ingredient whose name is used to generate the map key
   * @return the generated map key in lowercase
   */
  private String generateMapKey(Ingredient ingredient) {
    return ingredient.getName().toLowerCase();
  }

  /**
   * Checks if the specified ingredient is present in the storage and if it has the same expiry date.
   *
   * @param ingredientToCheck The ingredient to be checked in the storage.
   * @return true if the ingredient is present and has the same expiry date, false otherwise.
   */
  private boolean isIngredientExpiryMatching(Ingredient ingredientToCheck) {
    String mapKey = generateMapKey(ingredientToCheck);
    LocalDate ingredientExpiryDate = ingredientToCheck.getExpiryDate();
    return ingredientMap.get(mapKey).stream()
        .anyMatch(ingredient -> ingredient.getExpiryDate().isEqual(ingredientExpiryDate));
  }
}
