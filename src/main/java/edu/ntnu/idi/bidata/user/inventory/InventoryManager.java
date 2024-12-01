package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.OutputHandler;
import edu.ntnu.idi.bidata.util.Utility;
import edu.ntnu.idi.bidata.util.command.ValidCommand;
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
 * @version 2024-12-01
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
    outputHandler.printOutput("#### Add ingredient ####");
    String name = collectIngredientName(ingredientName);
    UserInput amountAndUnit = collectAmountAndUnit();
    float amount = amountAndUnit.getAmount();
    ValidUnit unit = amountAndUnit.getUnit();
    float value = collectIngredientValue();
    int daysUntilExpiry = collectDaysUntilExpiry();

    return new Ingredient(name, amount, unit, value, daysUntilExpiry);
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

  private UserInput collectAmountAndUnit() {
    UserInput userInput = null;
    boolean validInput = false;
    while (!validInput) {
      outputHandler.printInputPrompt("Please enter the amount with unit:");
      try {
        userInput = inputScanner.fetchUnit();
        if (userInput.getCommand() == ValidCommand.UNKNOWN) {
          throw new IllegalArgumentException("");
        }
        validInput = true;
      } catch (IllegalArgumentException ignored) {
      }
    }

    return userInput;
  }

  private String collectIngredientName(String inputName) {
    String name = inputName;
    if (inputName == null) {
      outputHandler.printInputPrompt("Please enter the ingredient name:");
      name = inputScanner.collectValidString();
    }
    return name;
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

  public void removeIngredientFromCurrent(String ingredientName) {
    assertInventoryIsAvailable();
    List<Ingredient> ingredientList = findIngredientFromCurrent(ingredientName);
    if (ingredientList == null) {
      outputHandler.printOutput("There is no " + ingredientName + " at " + currentStorage.getStorageName());
    } else {
      // passed all checks
      outputHandler.printOutput("Please select the ingredient to delete:");
      printList(ingredientList);
      outputHandler.printInputPrompt();
      int index = inputScanner.collectValidInteger();
      if (index > ingredientList.size() || index < 1) {
        throw new IllegalArgumentException("Invalid index, please try again:");
      }
      // user input from 1, index starts from 0 (N.B. index -1)
      currentStorage.removeIngredient(ingredientList.get(index - 1));
      outputHandler.printEndResultWithLineBreak(true, "removed", ingredientName);
    }
  }

  private void printList(List<?> list) {
    for (int index = 0; index < list.size(); index++) {
      outputHandler.printOutput(index + 1 + ":" + list.get(index));
    }
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
  private void creatMapEntry(String storageName) {
    storageMap.put(Utility.createKey(storageName), new IngredientStorage(storageName));
  }

  /**
   * Ensures that the current inventory is not null.
   *
   * @throws IllegalArgumentException if no inventory is currently selected.
   */
  public void assertInventoryIsAvailable() {
    if (currentStorage == null) {
      throw new IllegalArgumentException("You are currently not in an inventory, please use the 'go' command.");
    }
  }

}
