package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.user.inventory.Inventory;
import edu.ntnu.idi.bidata.user.recipe.CookBook;
import edu.ntnu.idi.bidata.util.OutputHandler;

import java.util.HashMap;
import java.util.Objects;

/**
 * The User class encapsulates information about a user, including their name,
 * personal inventory, cookbook, current directory, and output handler. It provides
 * methods to interact with and manage these attributes.
 *
 * @author Nick Heggø
 * @version 2024-10-31
 */
public class User {
  private String name;
  private HashMap<String, Inventory> userInventory;
  private CookBook cookBook;
  private Object currentDirectory;
  private OutputHandler outputHandler;

  /**
   * Constructs a new User object with the given name, initializes various components and default inventory.
   *
   * @param name The name of the user to be created.
   */
  public User(String name) {
    setName(name);
    userInventory = new HashMap<>();
    cookBook = new CookBook();
    addInventory("Home");
    addStorage("Home", "Fridge");
    outputHandler= new OutputHandler();

  }

  /**
   * Sets the current directory for the User instance.
   *
   * @param currentDirectory the directory object to set as the current directory
   */
  public void setCurrentDirectory(Object currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  /**
   * Processes the given user input and executes the corresponding command.
   * Valid subcommands include "cookbook" or an inventory key present in the user's inventory.
   * If the subcommand is "cookbook", it sets the current directory to the cookbook and lists the recipes.
   * If the subcommand matches an inventory key, it sets the current directory to that inventory and prints the inventory contents.
   * Otherwise, it prints a message indicating that the subcommand was not found.
   *
   * @param userInput The user input containing the main command and an optional subcommand.
   * @throws IllegalArgumentException if no subcommand is given.
   */
  public void go(UserInput userInput) {
    if (!userInput.hasSubcommand()) {
      throw new IllegalArgumentException("No subcommand given");
    }

    String subcommand = userInput.getSubcommand();

    if (subcommand.equals("cookbook")){
      setCurrentDirectory(cookBook);
      outputHandler.printOutputWithLineBreak(cookBook.listRecipes());
    } else if (userInventory.containsKey(subcommand)) {
      Inventory inventory = userInventory.get(subcommand);
      setCurrentDirectory(inventory);
      outputHandler.printOutputWithLineBreak(inventory.getInventoryString());
    } else {
      outputHandler.printOutput(subcommand + " not found.");
    }

  }


  public Object getCurrentDirectory() {
    return this.currentDirectory;
  }

  public void addInventory(String name, Inventory inventory) {
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

  public boolean addInventory(String name) {
    return userInventory.putIfAbsent(name, new Inventory()) == null;
  }

  public boolean addStorage(String inventoryName, String storageName) {
    Inventory selectedInventory = this.userInventory.get(inventoryName);
    return selectedInventory.addNewCollection(storageName);
  }


}
