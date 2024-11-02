package edu.ntnu.idi.bidata.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The AddCommand class extends the Command class and is responsible for handling
 * the "add" command by processing subcommands related to adding new items
 * such as locations and storage entries for a given user.
 *
 * @author Nick Heggø
 * @version 2024-11-02
 */
public class AddCommand extends Command {

  /**
   * Constructs a new AddCommand object for the given user. This constructor
   * initializes the necessary components and processes the command related
   * to adding new entries.
   *
   * @param user The user for whom the add command is being created and processed.
   */
  public AddCommand(User user) {
    super(user);
  }

  /**
   * Processes the subcommand provided by the user input. This method is responsible
   * for determining which subcommand to execute based on the user's input string and
   * delegating the task to the appropriate method for handling that subcommand.
   * The method handles specific subcommands such as "location" and "storage"
   * by calling their respective methods. If the subcommand does not match any
   * recognized subcommands, an {@link IllegalArgumentException} is thrown.
   * Subcommands processed:
   * - "location": Calls {@code addLocation()} to handle this subcommand.
   * - "storage": Calls {@code addStorage()} to handle this subcommand.
   *
   * @throws IllegalArgumentException if the subcommand does not match any recognized subcommands.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "location" -> addLocation();
      case "storage" -> addStorage();
      case "ingredient" -> addIngredient();
      case "recipe" -> addRecipe();
      default -> throw new IllegalArgumentException("Unexpected subcommand: "
          + userInputSubcommand);
    }
  }

  private void addLocation() {
    String locationName = null;
    if (userInputString != null) {
      locationName = userInputString;
    } else {
      outputHandler.printOutput("Please enter new location name:");
      locationName = inputScanner.getUserInput();
    }
    boolean success = user.addLocation(locationName);
    String message = success
        ? locationName + " has been added to the location list" :
        "Failed to add " + locationName + " to the list";
    outputHandler.printOutput(message);
  }

  /**
   * Adds a new storage to the user's storage list. This method prompts the user
   * to input a storage name and then adds it to the user's list of storages. If
   * the input is blank, an {@link IllegalArgumentException} is thrown.
   * The method relies on the user and inputScanner instances available in the
   * outer class to get the user input and add the storage.
   */
  private void addStorage() {
    String storageName = inputScanner.getUserInput();
    user.addStorage(storageName);
  }

  private void addIngredient() {
    // TODO
  }

  private void addRecipe() {
    // TODO
  }

}
