package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.Utility;
import edu.ntnu.idi.bidata.util.input.UnitInput;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Manages multiple ingredient storages and provides functionalities
 * for ingredient and storage management.
 *
 * @author Nick Heggø
 * @version 2024-12-04
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
  }

  public IngredientStorage getStorage(String storageName) {
    return storageMap.get(Utility.createKey(storageName));
  }

  /**
   * Adds an ingredient to the current storage after validating inventory availability.
   * Takes input from the user and creates an Ingredient object.
   */
  public Ingredient createIngredient(String ingredientName) {
    outputHandler.printOutput("#### Add ingredient ####");
    UnitInput amountAndUnit = collectAmountAndUnit();
    float amount = amountAndUnit.getAmount();
    ValidUnit unit = amountAndUnit.getUnit();
    float value = collectIngredientValue();
    int daysUntilExpiry = collectDaysUntilExpiry();

    return new Ingredient(ingredientName, amount, unit, value, daysUntilExpiry);
  }

  /**
   * Retrieves an overview of ingredients in the current storage.
   *
   * @return A list of strings representing the names of ingredients in the current storage.
   */
  public List<String> getIngredientOverview() {
    assertInventoryIsAvailable();
    return currentStorage.getIngredientOverview();
  }

  /**
   * Retrieves the names of all ingredient storages in the inventory.
   *
   * @return A list containing the names of all storages currently in the storage map.
   */
  public List<String> getStorageOverview() {
    return storageMap.values().stream()
        .map(IngredientStorage::getStorageName)
        .toList();
  }

  private int collectDaysUntilExpiry() {
    int daysTilExpiry;
    do {
      outputHandler.printInputPrompt("Please enter the days until expiry:");
      daysTilExpiry = inputScanner.collectValidInteger();
    } while (daysTilExpiry < -1);
    return daysTilExpiry;
  }

  private float collectIngredientValue() {
    float value;
    do {
      outputHandler.printInputPrompt("Please enter the ingredient value:");
      value = inputScanner.collectValidFloat();
    } while (value < 0.0f);
    return value;
  }

  private UnitInput collectAmountAndUnit() {
    UnitInput input = null;
    boolean validInput = false;
    while (!validInput) {
      outputHandler.printInputPrompt("Please enter the amount with unit:");
      try {
        input = inputScanner.fetchUnit();
        validInput = true;
      } catch (IllegalArgumentException ignored) {
      }
    }

    return input;
  }

  /**
   * Adds an ingredient to the current storage.
   *
   * @param ingredientToBeAdded The ingredient to be added to the storage.
   */
  public void addIngredientToCurrentStorage(Ingredient ingredientToBeAdded) {
    assertInventoryIsAvailable();
    currentStorage.addIngredient(ingredientToBeAdded);
  }

  public void removeIngredientFromCurrent(String ingredientName) {
    assertInventoryIsAvailable();
    List<Ingredient> ingredientList = findIngredientFromCurrent(ingredientName);
    if (ingredientList == null) {
      throw new IllegalArgumentException("There is no " + ingredientName + " at " + currentStorage.getStorageName());
    }

    // passed all checks
    if (ingredientList.size() == 1) {
      currentStorage.removeIngredient(ingredientList.getFirst());
    } else {
      outputHandler.printOutput("Please select the ingredient to delete:");
      outputHandler.printList(ingredientList, "numbered");
      outputHandler.printInputPrompt();
      int index = inputScanner.collectValidInteger();
      if (index > ingredientList.size() || index < 1) {
        throw new IllegalArgumentException("Invalid index, please try again:");
      }
      // user input from 1, index starts from 0 (N.B. index -1)
      currentStorage.removeIngredient(ingredientList.get(index - 1));
    }
    outputHandler.printOperationStatus(true, "removed", ingredientName);
  }

  public List<Ingredient> findIngredientFromCurrent(String ingredientName) {
    assertInventoryIsAvailable();
    return currentStorage.getIngredientList(ingredientName);
  }

  public String getTotalValue() {
    float sum = storageMap.values().stream()
        .map(IngredientStorage::getAllValue)
        .reduce(0.0f, Float::sum);
    sum = Math.round(sum * 100) / 100.0f;
    return "Inventory has total value of: " + sum + " kr.";
  }

  public boolean removeStorage(String storageName) {
    return storageMap.remove(Utility.createKey(storageName)) != null;
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
        .forEach(string -> stringBuilder.append("\n").append(string));
    return stringBuilder.toString();
  }

  public float removeAllExpired() {
    assertInventoryIsAvailable();
    List<Ingredient> expired = currentStorage.getAllExpired();
    currentStorage.removeExpired();
    return expired.stream()
        .map(Ingredient::getValue)
        .reduce(0.0f, Float::sum);
  }

  /**
   * Adds a new ingredient storage to the storage map with the provided name.
   * The storage name is converted to lowercase before being used as the key in the map.
   *
   * @param storageName The name of the storage to be added.
   */
  public void createIngredientStorage(String storageName) {
    storageMap.put(Utility.createKey(storageName), new IngredientStorage(storageName));
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
