package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.user.inventory.Inventory;
import edu.ntnu.idi.bidata.user.recipe.CookBook;

import java.util.HashMap;
import java.util.Stack;

/**
 * The User class encapsulates information about a user, including their name,
 * personal inventory, cookbook, current directory, and output handler. It provides
 * methods to interact with and manage these attributes.
 *
 * @author Nick Heggø
 * @version 2024-11-02
 */
public class User {
  private String name;
  private HashMap<String, Inventory> userInventory;
  private CookBook cookBook;
  private Inventory currentDirectory;
  private UserInput userInput;
  private Stack<Inventory> historyDirectory;

  /**
   * Constructs a new User object with the given name, initializes various components and default inventory.
   *
   * @param name The name of the user to be created.
   */
  public User(String name) {
    setName(name);
    userInventory = new HashMap<>();
    cookBook = new CookBook();
    historyDirectory = new Stack<>();
    addLocation("Home");
    addLocation("Office");
    goToLocation("Home");
    addStorage("Fridge");
    addStorage("Cold Room");
    goToLocation("Office");
    addStorage("Office Fridge");
  }

  public void setUserInput(UserInput userInput) {
    this.userInput = userInput;
  }

  public UserInput getUserInput() {
    return this.userInput;
  }
  /**
   * Sets the current directory for the User instance.
   *
   * @param currentDirectory the directory object to set as the current directory
   */
  public void setCurrentDirectory(Inventory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public Inventory getCurrentDirectory() {
    if (this.currentDirectory == null) {
      throw new IllegalArgumentException("You are not at a inventory, please the the go command");
    }
    return this.currentDirectory;
  }

  public String listInventory() {
    getCurrentDirectory();
    // TODO missing string "inventory name has storages such as..."
    return this.currentDirectory.getInventoryString();
  }

  public boolean isInDirectory() {
    return currentDirectory != null;
  }

  public void addIngredient() {
    if (this.currentDirectory == null) {
      throw new IllegalArgumentException("You're currently not in a directory, use go command");
    }
//    TODO
  }

  /**
   * Changes the current directory of the user to the specified directory name if it exists in the user's inventory.
   * Throws an IllegalArgumentException if the specified directory name is not found in the user's inventory.
   *
   * @param locationName The name of the directory to switch to.
   * @throws IllegalArgumentException if the specified directory name is not found in the user's inventory.
   */
  public String goToLocation(String locationName) {
    String returnString = "";
    if (userInventory.containsKey(locationName)) {
      currentDirectory = userInventory.get(locationName);
      returnString = returnCurrentLocationString(locationName);
    } else {
      throw new IllegalArgumentException("No directory name found with: " + locationName);
    }
    return returnString;
  }

  /**
   * Constructs a string indicating the user's current location along with the inventory details.
   *
   * @param locationName The name of the current location of the user.
   * @return A string describing the user's current location and the inventory details in the directory.
   */
  public String returnCurrentLocationString(String locationName) {
    String returnString = "You are currently at " + locationName;
    returnString += this.currentDirectory.getInventoryString();
    return returnString;
  }


  public void addLocation(String name, Inventory inventory) {
    this.userInventory.put(name, inventory);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getInventoryString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Inventory:");
    userInventory.values().forEach(
        inventory -> stringBuilder.append(inventory.getInventoryString())
    );
    return stringBuilder.toString();
  }

  public boolean addLocation(String name) {
    return userInventory.putIfAbsent(name, new Inventory()) == null;
  }

  public boolean addStorage(String storageName) {
    return currentDirectory.addNewCollection(storageName);
  }


}
