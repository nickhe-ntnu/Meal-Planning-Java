package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.IngredientStorage;

/**
 * Represents a command to navigate to different locations or storages, or to go back
 * to the previous directory for a user.
 *
 * @author Nick Heggø
 * @version 2024-11-03
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
   * Processes the subcommand input by the user and delegates execution to
   * the appropriate method based on the subcommand value.
   * The method interprets the following subcommands:
   * - "location": Calls the goLocation() method to handle navigation to a location.
   * - "storage": Calls the goStorage() method to handle navigation to storage.
   * - "back": Calls the goBack() method to handle navigation back to the previous directory.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "to" -> goTo();
      case "back" -> goBack();
      default -> illegalCommand();
    }
  }

  private void goTo() {
    String inputString = requestInputIfNeeded("Please enter the storage name:").toLowerCase();
    IngredientStorage ingredientStorage = inventoryManager.getStorage(inputString);
    boolean success = ingredientStorage != null;
    if (success) {
      inventoryManager.setCurrentStorage(ingredientStorage);
      addToHistory(inventoryManager.getCurrentStorage());
    }
    printOperationMessage(success, inputString);
  }

  private void goBack() {
    if (history == null) {
      System.out.println("here we go again;");
    }
    if (history.isEmpty()) {
      outputHandler.printOutput("History is empty, there is no where to go");
    } else {
      inventoryManager.setCurrentStorage(removeFromHistory());
      printOperationMessage(true, inventoryManager.getCurrentStorage().getStorageName());
    }
  }

  private void addToHistory(IngredientStorage storage) {
    history.addLast(storage);
  }

  private IngredientStorage removeFromHistory() {
    return history.removeLast();
  }

  private void printOperationMessage(boolean success, String locationName) {
    if (success) {
      outputHandler.printOutput("You are now at " + inventoryManager.getCurrentStorage().getStorageName());
    } else {
      outputHandler.printOutput("Fail to locate destination " + userInputString);
    }
  }
}
