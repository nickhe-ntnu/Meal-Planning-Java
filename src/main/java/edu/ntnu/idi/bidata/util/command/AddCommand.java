package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;

/**
 * The AddCommand class extends the Command class and is responsible for handling
 * the "add" command by processing subcommands related to adding new items
 * such as locations and storage entries for a given user.
 *
 * @author Nick Heggø
 * @version 2024-11-09
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
   * Processes the subcommand provided by the user input.
   * This method determines which specific "add" subcommand needs to be executed
   * (e.g., addLocation, addStorage, addIngredient, addRecipe) and calls the
   * corresponding method. If the subcommand is not recognized, an
   * {@code IllegalArgumentException} is thrown.
   *
   * @throws IllegalArgumentException if the provided subcommand is unexpected.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "storage" -> addStorage();
      case "ingredient" -> addIngredient();
      case "recipe" -> addRecipe();
      default -> throw new IllegalArgumentException("Unexpected subcommand: "
          + userInputSubcommand);
    }
  }

  /**
   * Prints a success or failure message for an operation.
   *
   * @param success A boolean indicating whether the operation was successful.
   */
  private void printOperationMessage(boolean success) {
    if (success) {
      outputHandler.printOperationSuccessMessage("added", userInputString);
    } else
      outputHandler.printOperationFailedMessage("add", userInputString);
  }

  /**
   * Adds a new storage entry specified by the user's input.
   * This method prompts the user for a storage name and attempts to add it
   * to the user's storage map. If the addition is successful, an operation
   * success message is printed. If not, an operation failure message is printed.
   */
  private void addStorage() {
    getInputString("Please enter new storage name:");
    boolean success = user.addStorage(userInputString);
    printOperationMessage(success);
  }

  /**
   * Adds a new ingredient to the user's current storage.
   * This method first checks if the user is located in a directory.
   * If the user is not in a directory, an {@code IllegalArgumentException} is thrown.
   * Then, the method launches a wizard to collect the details of the ingredient
   * and subsequently adds the ingredient to the user's list.
   * Finally, it prints a success message indicating that the ingredient has been added.
   *
   * @throws IllegalArgumentException if the user is not in a directory.
   */
  private void addIngredient() {
    if (user.getCurrentStorage() == null) {
      throw new IllegalArgumentException("You are currently not in a directory, please use the 'go' command");
    }
    Ingredient ingredient = addIngredientWizard();
    user.addIngredient(ingredient);
    outputHandler.printOutputWithLineBreak("Successfully added " + ingredient.getName());
  }

  private void addRecipe() {
    // TODO
  }

  private Ingredient addIngredientWizard() {
    outputHandler.printOutput("#### Add ingredient ####");

    outputHandler.printOutput("Please enter the ingredient name:");
    String name = inputScanner.getValidString();

    outputHandler.printOutput("Please enter the amount with unit:");
    UserInput userInput = inputScanner.fetchUnit();
    while (userInput.getValidUnit() == ValidUnit.UNKNOWN) {
      System.out.println("Type error, please ensure to use a valid unit");
      userInput = inputScanner.fetchUnit();
    }
    float amount = userInput.getUnitAmount();
    ValidUnit unit = userInput.getValidUnit();

    outputHandler.printOutput("Please enter the unit price");
    float unitPrice = inputScanner.getValidFloat();

    outputHandler.printOutput("Please enter days until expiry date");
    int dayToExpiry = (int) inputScanner.getInputFloat();

    return new Ingredient(name, amount, unit, unitPrice, dayToExpiry);
  }

}
