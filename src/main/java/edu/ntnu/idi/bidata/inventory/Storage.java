package edu.ntnu.idi.bidata.inventory;

import edu.ntnu.idi.bidata.ingredient.Ingredient;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a storage of ingredients.
 */
public class Storage {

  private HashMap<String, HashSet<Ingredient>> namedStorages;

  public Storage() {
    namedStorages = new HashMap<>();
  }

  public boolean collectionExists(String storage) {
    return namedStorages.containsKey(storage);
  }

  public void createCollection(String storage) {
    if (!collectionExists(storage)) {
      namedStorages.put(storage, new HashSet<>());
    } else {
      throw new IllegalArgumentException("Storage already exists");
    }
  }

  public void addIngredient(String storage, Ingredient ingredient) {
    if (collectionExists(storage)) {
      namedStorages.get(storage).add(ingredient);
    } else {
      throw new IllegalArgumentException("Storage does not exist");
    }
  }

  public void removeIngredient(String storage, Ingredient ingredient) {
    if (collectionExists(storage)) {
      namedStorages.get(storage).remove(ingredient);
    } else {
      throw new IllegalArgumentException("Collection does not exist");
    }
  }

  public boolean collectionContainsIngredient(String storage, String ingredientName) {
    if (collectionExists(storage)) {
      return namedStorages.get(storage).stream()
          .map(Ingredient::getName)
          .anyMatch(name -> name.equals(ingredientName));
    } else {
      throw new IllegalArgumentException("Collection does not exist");
    }
  }

  public void getIngredientFromStorage(String ingredientName) {
    namedStorages.forEach((storage, ingredients) -> ingredients.stream()
        .filter(ingredient -> ingredient.getName().equals(ingredientName))
        .forEach(ingredient -> System.out.println("Ingredient " + ingredientName + " is in storage: " + storage)));
  }

  public void printAllInventory() {
    namedStorages.forEach((keys, collections) -> {
      System.out.println(keys);
      collections.forEach(ingredient -> {
        System.out.println(ingredient.toString());
      });

    });
  }

}
