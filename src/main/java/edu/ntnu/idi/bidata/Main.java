package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.inventory.Inventory;

import java.util.*;

/**
 * The main starting point of your application. Let this class create the
 * instance of your main-class that starts your application.
 */
public class Main {

  private final Scanner inputScanner;

  private String userName;
  private Inventory userInventory;


  /**
   * Constructor for the Main class.
   */
  public Main() {
    inputScanner = new Scanner(System.in);
    userInventory = new Inventory();
    userInventory.addStorage("Refrigerator");
  }

  public Main(Scanner scanner) {
    this.inputScanner = scanner;
    userInventory = new Inventory();
    userInventory.addStorage("Refrigerator");
  }

  public String scanUserInput() {
    String scannedInput = inputScanner.nextLine().trim();
    if (scannedInput.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank, please try again.");
    }
    return scannedInput;
  }

  public int getUserAction() {
    int userAction;
    String scannedInput = inputScanner.nextLine().replaceAll("\\s", "");
    if (scannedInput.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank, please try again.");
    }

    try {
      userAction = Integer.parseInt(scannedInput);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Input '" + scannedInput + "'is not an integer, please try again.");
    }

    if (userAction < 0 || userAction > 6) {
      throw new IllegalArgumentException("Input '" + userAction + "' is out of valid range (0-6), please try again.");
    }

    return userAction;
  }

  public static void formatPrint(String message, Runnable task) {
    System.out.println("###############################\n" + message);
    task.run();
    System.out.println("################################");
  }

  /**
   * List the menu for the user to select from.
   */
  public int printAction() {
    System.out.println("""
        Please select an operation:
        1. List all inventory
        2. Find Ingredient
        3. Add Ingredient
        4. Add more Storage
        5. Create an recipe
        6. Available recipe
        0. Exit the program.""");
    int userAction = -1; // Set = -1 to satisfy the compiler.
    boolean inputChanged = false;
    while (!inputChanged) {
      try {
        userAction = getUserAction();
        inputChanged = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    return userAction;
  }

  public void userSetup() {
    while (getUserName() == null || getUserName().isBlank()) {
      try {
        System.out.println("Please setup your username:");
        setUserName();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    System.out.println("Welcome " + getUserName() + ", hope you enjoy using the app!");
  }

  public void setUserName() {
    String scannedString = scanUserInput();
    if (scannedString == null || scannedString.isBlank()) {
      throw new IllegalArgumentException("Username cannot be blank.");
    }
    this.userName = scannedString.replaceAll("\\s", "");
  }

  public String getUserName() {
    return userName;
  }

  public void addStorage() {
    boolean validInput = false;
    while (!validInput) {
      try {
        String scannedString = scanUserInput();
        userInventory.addStorage(scannedString);
        System.out.println(scannedString + " was successfully added.");
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }


  public void printInventory() {
    formatPrint("List of all inventory:", () -> userInventory.printInventory());
  }


  public void printAddStorage() {
    formatPrint("Please enter the new storage name:", this::addStorage);
  }

  /**
   * The main application loop.
   */
  public void app() {
    System.out.println("""
        Thank you for using the meal planning app! Earth thank you for taking care of her.
        Welcome new user""");
    userSetup();
    boolean appRunning = true;
    while (appRunning) {
      int userAction = printAction();
      switch (userAction) {
        case 1 -> printInventory();
        case 4 -> printAddStorage();
        case 0 -> {
          System.out.println("Program Exiting...");
          appRunning = false;
        }
        default -> System.out.println("Sorry, option "
            + userAction + " isn't available right now, please try again later.");
      }
    }
  }

  public static void main(String[] args) {
    Main user = new Main();
    user.app();
  }
}
