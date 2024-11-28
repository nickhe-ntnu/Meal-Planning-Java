package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.command.Utility;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.*;

/**
 * Manages multiple ingredient storages and provides functionalities
 * for ingredient and storage management.
 *
 * @author Nick Heggø
 * @version 2024-11-27
 */
public class InventoryManager {

  private final InputScanner inputScanner;
  private final OutputHandler outputHandler;

  private final Map<String, IngredientStorage> storageMap;
  private final Stack<IngredientStorage> history;
  private IngredientStorage currentStorage;

  public InventoryManager(InputScanner inputScanner, OutputHandler outputHandler) {
    this.inputScanner = inputScanner;
    this.outputHandler = outputHandler;
    storageMap = new HashMap<>();
    history = new Stack<>();
    history.add(null);
  }

  public IngredientStorage getStorage(String storageName) {
    return storageMap.get(Utility.createKey(storageName));
  }

  /**
   * Adds an ingredient to the current storage after validating inventory availability.
   * Takes input from the user and creates an Ingredient object.
   */
  public Ingredient createIngredient(String ingredientName) {
    return inputIngredientDetails(ingredientName);
  }

  /**
   * Adds an ingredient to the current storage.
   *
   * @param ingredientToBeAdded The ingredient to be added to the storage.
   */
  public void addIngredient(Ingredient ingredientToBeAdded) {
    assertInventoryIsAvailable();
    currentStorage.addIngredient(ingredientToBeAdded);
  }

  public List<String> findAllIngredient(String ingredientName) {
    ArrayList<String> listOfStorageContainsIngredient = new ArrayList<>();
    storageMap.values().forEach(storage -> {
      if (storage.isIngredientPresent(ingredientName)) {
        listOfStorageContainsIngredient.add(storage.getStorageName());
      }
    });
    return listOfStorageContainsIngredient;
  }

  public List<Ingredient> fingIngredientList(String ingredientName) {
    assertInventoryIsAvailable();
    return currentStorage.getIngredientList(ingredientName);
  }

  /**
   * Adds a new storage to the user's storage map if it is not already present.
   *
   * @param storageName The name of the storage to be added.
   * @return true if the storage was successfully added; false otherwise.
   */
  public void createStorage(String storageName) {
    creatMapEntry(storageName);
  }

  public String getInventoryString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("###### Inventory #######");
    storageMap.values()
        .forEach(storage -> stringBuilder.append(storage.getStorageString()));
    return stringBuilder.toString();
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

  public String getStorageNameString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("####### Storages #######");
    storageMap.values().stream()
        .map(IngredientStorage::getStorageName)
        .forEach(name -> stringBuilder.append("\n# ").append(name));
    return stringBuilder.toString();
  }

  public IngredientStorage getCurrentStorage() {
    return currentStorage;
  }

  public void setCurrentStorage(IngredientStorage storage) {
    currentStorage = storage;
  }

  public void setCurrentStorage(String storageName) {
    IngredientStorage storage = getStorage(Utility.createKey(storageName));
    if (storage != null) {
      currentStorage = storage;
    }
  }

  /**
   * Retrieves a string representation of all expired ingredients in the current inventory.
   *
   * @return A string listing all expired ingredients.
   */
  public String getExpiredString() {
    assertInventoryIsAvailable();
    List<Ingredient> listOfExpired = currentStorage.getAllExpired();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("####### Expired ########");
    listOfExpired.stream()
        .map(Ingredient::toString)
        .forEach(stringBuilder::append);
    return stringBuilder.toString();
  }

  public void removeExpired() {
    assertInventoryIsAvailable();
    List<Ingredient> expired = currentStorage.getAllExpired();
    currentStorage.removeExpired();
    //    int sum = expired.stream().map(Ingredient::getAmount).reduce(0, Integer::sum);
  }

  /**
   * Adds a new ingredient storage to the storage map with the provided name.
   * The storage name is converted to lowercase before being used as the key in the map.
   *
   * @param storageName The name of the storage to be added.
   */
  private void creatMapEntry(String storageName) {
    storageMap.put(Utility.createKey(storageName), new IngredientStorage(storageName));
  }

  private Ingredient inputIngredientDetails(String ingredientName) {
    outputHandler.printOutput("#### Add ingredient ####");
    String name = ingredientName;
    if (name == null) {
      outputHandler.printInputPrompt("Please enter the ingredient name:");
      name = inputScanner.collectValidString();
    }

    outputHandler.printInputPrompt("Please enter the amount with unit:");
    UserInput userInput = inputScanner.fetchUnit();
    while (userInput.getValidUnit() == ValidUnit.UNKNOWN) {
      outputHandler.printInputPrompt("Type error, please ensure to use a valid unit");
      userInput = inputScanner.fetchUnit();
    }
    float amount = userInput.getUnitAmount();
    ValidUnit unit = userInput.getValidUnit();

    outputHandler.printInputPrompt("Please enter the unit price");
    float unitPrice = inputScanner.collectValidFloat();

    outputHandler.printInputPrompt("Please enter days until expiry date");
    int dayToExpiry = (int) inputScanner.getInputFloat();

    return new Ingredient(name, amount, unit, unitPrice, dayToExpiry);
  }

  /**
   * Ensures that the current inventory is not null.
   *
   * @throws IllegalArgumentException if no inventory is currently selected.
   */
  private void assertInventoryIsAvailable() {
    if (currentStorage == null) {
      throw new IllegalArgumentException("You are currently not in an inventory, please use the 'go' command.");
    }
  }

}
