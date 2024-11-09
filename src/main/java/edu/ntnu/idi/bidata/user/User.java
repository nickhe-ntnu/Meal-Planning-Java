package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;
import edu.ntnu.idi.bidata.user.recipe.CookBook;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.*;

/**
 * The User class encapsulates information about a user, including their name,
 * personal inventory, cookbook, current directory, and output handler. It provides
 * methods to interact with and manage these attributes.
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
public class User {
  private final Map<String, IngredientStorage> storageMap;
  private final CookBook cookBook;
  private final Stack<IngredientStorage> history;
  private String name;
  private UserInput input;
  private IngredientStorage currentStorage;

  /**
   * Constructs a new User object with the given name, initializes various components and default inventory.
   *
   * @param name The name of the user to be created.
   */
  public User(String name) {
    setName(name);
    storageMap = new HashMap<>();
    cookBook = new CookBook();
    history = new Stack<>();

    addStorage("Fridge");
    setCurrentStorage(getStorage("fridge"));
    addIngredient(new Ingredient("Chocolate", 300, ValidUnit.G, 10, 4));
    addStorage("Cold Room");
    addStorage("Office Fridge");
  }

  public IngredientStorage getCurrentStorage() {
    return currentStorage;
  }

  public void setCurrentStorage(IngredientStorage storage) {
    currentStorage = storage;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInventoryString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("###### Inventory #######");
    storageMap.values().forEach(storage -> stringBuilder.append(storage.getStorageString()));
    return stringBuilder.toString();
  }

  public UserInput getInput() {
    return this.input;
  }

  public void setInput(UserInput input) {
    this.input = input;
  }

  public Stack<IngredientStorage> getHistory() {
    return history;
  }

  public String getStorageString() {
    if (currentStorage == null) {
      throw new IllegalArgumentException("You're currently not in a storage, use the 'go' command");
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("#### Current Storage ###");
    stringBuilder.append(currentStorage.getStorageString());
    return stringBuilder.toString();
  }

  public IngredientStorage getStorage(String storageName) {
    return storageMap.get(storageName);
  }

  public void addIngredient(Ingredient ingredient) {
    currentStorage.addIngredient(ingredient);
  }

  public List<String> findIngredient(String ingredientName) {
    ArrayList<String> listOfStorageContainsIngredient = new ArrayList<>();
    storageMap.values().forEach(storage -> {
      if (storage.isIngredientPresent(ingredientName)) {
        listOfStorageContainsIngredient.add(storage.getStorageName());
      }
    });
    return listOfStorageContainsIngredient;
  }

  /**
   * Adds a new storage to the user's storage map if it is not already present.
   *
   * @param storageName The name of the storage to be added.
   * @return true if the storage was successfully added; false otherwise.
   */
  public boolean addStorage(String storageName) {
    boolean success = !isStoragePresent(storageName);
    if (success) {
      putToMap(storageName);
    }
    return success;
  }

  /**
   * Checks if a storage with the specified name is present in the storage map.
   * The storage name is case-insensitive.
   *
   * @param storageName The name of the storage to check for.
   * @return true if the storage is present; false otherwise.
   */
  private boolean isStoragePresent(String storageName) {
    return storageMap.containsKey(storageName.toLowerCase());
  }

  /**
   * Adds a new ingredient storage to the storage map with the provided name.
   * The storage name is converted to lowercase before being used as the key in the map.
   *
   * @param storageName The name of the storage to be added.
   */
  private void putToMap(String storageName) {
    storageMap.put(storageName.toLowerCase(), new IngredientStorage(storageName));
  }
}
