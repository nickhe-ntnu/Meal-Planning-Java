package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;

import java.util.List;

/**
 * Represents a command to navigate to different locations or storages, or to go back
 * to the previous directory for a user.
 *
 * @author Nick Heggø
 * @version 2024-12-08
 */
public class GoCommand extends Command {

  /**
   * Constructs a new GoCommand object for the specified user, enabling
   * the user to navigate through different locations, storages, or to
   * go back to the previous directory.
   *
   * @param user The user for whom the GoCommand is being created and processed.
   */
  public GoCommand(User user) {
    super(user);
  }

  /**
   * Processes the current subcommand and executes the corresponding method.
   */
  @Override
  public void execute() {
    switch (getSubcommand()) {
      case "to" -> goTo();
      case "back" -> goBack();
      default -> illegalCommand();
    }
  }

  /**
   * Navigates to the specified ingredient storage within the inventory.
   * Lists all available storages and prompts the user to enter the desired storage name if not provided.
   * Updates the current storage to the specified one if it exists, and prints a confirmation or failure message.
   */
  private void goTo() {
    if (isArgumentEmpty()) {
      List<String> storageOverview = getInventoryManager().getStorageOverview();
      getOutputHandler().printOutput("List of available storages:");
      getOutputHandler().printList(storageOverview, "bullet");
      setArgument("Please enter the storage name:");
    }
    IngredientStorage ingredientStorage = getInventoryManager().getStorage(getArgument());
    boolean success = ingredientStorage != null;
    if (success) {
      addToHistory(getInventoryManager().getCurrentStorage());
      getInventoryManager().setCurrentStorage(ingredientStorage);
    }
    printOperationMessage(success);
  }

  /**
   * Navigates back to the previous storage in the history.
   * If the history is empty, a message is printed indicating no available history to navigate.
   * Updates the current storage and prints a message indicating the success or failure of the operation.
   */
  private void goBack() {
    if (getHistory().isEmpty()) {
      getOutputHandler().printOutput("History is empty, there is no where to go");
    } else {
      getInventoryManager().setCurrentStorage(removeFromHistory());
      boolean status = getInventoryManager().getCurrentStorage() != null;
      printOperationMessage(status);
    }
  }

  /**
   * Adds the specified IngredientStorage to the navigation history.
   *
   * @param storage The IngredientStorage to be added to the history.
   */
  private void addToHistory(IngredientStorage storage) {
    getHistory().addLast(storage);
  }

  /**
   * Removes the last IngredientStorage from the navigation history.
   *
   * @return the last IngredientStorage that was removed from the history
   */
  private IngredientStorage removeFromHistory() {
    return getHistory().removeLast();
  }

  /**
   * Prints a message indicating the success or failure of a storage location operation.
   *
   * @param success      a boolean indicating whether the operation was successful
   * @param locationName the name of the location being attempted to access
   */
  private void printOperationMessage(boolean success) {
    if (success) {
      getOutputHandler().printOutput("You are now at " + getInventoryManager().getCurrentStorage().getStorageName());
    } else {
      getOutputHandler().printOutput("Fail to locate destination " + getArgument());
    }
  }
}
