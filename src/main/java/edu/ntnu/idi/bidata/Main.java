package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.ingredient.Ingredient;
import edu.ntnu.idi.bidata.inventory.Storage;

import java.util.*;

/**
 * The main starting point of your application. Let this class create the
 * instance of your main-class that starts your application.
 */
public class Main {
  private Scanner inputScanner;
  private int userInput;
  private ArrayList<String> listOfOutput;

  private String userName;
  private Storage userStorage;

  public Main() {
    inputScanner = new Scanner(System.in);
    userStorage = new Storage();
    listOfOutput = new ArrayList<>();
    userStorage.createCollection("Refrigerator");
    Ingredient apple = new Ingredient("Apple", "g", 200);
    Ingredient bannana = new Ingredient("Banana", "kg", 2);
    userStorage.addIngredient("Refrigerator", apple);
    userStorage.addIngredient("Refrigerator", bannana);
  }

  public void printList(String message, ArrayList<String> arrayList) {
    Iterator<String> it = arrayList.iterator();
    System.out.println("###############################\n" + message);
    while (it.hasNext()) {
      System.out.println("  * " + it.next());
    }
    System.out.println("################################");
    listOfOutput.clear();
  }

  public void printListV2(String message, HashMap<String, HashSet<Ingredient>> storageMap) {
    Iterator<String> stringIterator = storageMap.keySet().iterator();
    System.out.println("###############################\n" + message);
    while (stringIterator.hasNext()) {
      String itString= stringIterator.next();
      System.out.println("  * "+ itString);
      Iterator<Ingredient> ingredientIterator = storageMap.get(itString).iterator();
      while (ingredientIterator.hasNext()) {
        Ingredient ingredient = ingredientIterator.next();
        System.out.println("    - " + ingredient.getName() + ", " + ingredient.getAmount() + " " + ingredient.getUnit());
      }
    }
    System.out.println("################################");
  }

  public void userSetup(Scanner scanner) {
    while (getUserName() == null || getUserName().isBlank()) {
      try {
        System.out.println("Please setup your username:");
        setUserName(scanner);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    System.out.println("Welcome " + getUserName() + ", hope you enjoy using the app!");
  }

  public void setUserName(Scanner scanner) {
    String scannedInput = scanner.nextLine();
    if (scannedInput == null || scannedInput.isBlank()) {
      throw new IllegalArgumentException("Username cannot be blank.");
    }
    this.userName = scannedInput.replaceAll("\\s", "");
  }

  public String getUserName() {
    return userName;
  }

  public String getUserInput(Scanner scanner) {
    String scannedInput = scanner.nextLine().replaceAll("\\s", "");
    if (scannedInput.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank, please try again.");
    }
    return scannedInput;
  }

  public void setUserInput(Scanner scanner) {
    String scannedInput = scanner.nextLine().replaceAll("\\s", "");
    if (scannedInput.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank, please try again.");
    }

    try {
      userInput = Integer.parseInt(scannedInput);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Input is not an integer, please try again.");
    }

    if (userInput < 0 || userInput > 9) {
      throw new IllegalArgumentException("Input is out of valid range (0-9), please try again.");
    }
  }

  public int getUserInput() {
    return this.userInput;
  }

  public void listMenu() {
    System.out.println("""
        Please select an operation:
        1. List all inventory
        2. Find Ingredient
        3. Add Ingredient
        4. Add more Storage
        5. Create an recipe
        6. Available recipe
        0. Exit the program.""");
    boolean inputChanged = false;
    while (!inputChanged) {
      try {
        setUserInput(inputScanner);
        inputChanged = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void listStorages() {
    HashMap<String, HashSet<Ingredient>> storageMap = userStorage.getAllStorage();
    printListV2("List of all storage place: ", storageMap);
  }

  public void listIngredients() {

  }

/*
  public void addIngredient() {
    System.out.println("Enter ingredient name: ");
    String name = userInput.nextLine();
    System.out.println("Enter ingredient unit: ");
    String unit = userInput.nextLine();
    System.out.println("Enter ingredient amount: ");
    float amount = Float.valueOf(userInput.nextLine());
    Ingredient newIngredient = new Ingredient(name, unit, amount);
    System.out.println("Added ingredient: " + newIngredient.toString());
  }
*/

  public void addStorage() {
    System.out.println("###############################\n" +
        "Please enter the new storage name:");
    boolean validInput = false;
    while (!validInput) {
      try {
        String scannedInput = getUserInput(inputScanner);
        userStorage.createCollection(scannedInput);
        System.out.println(scannedInput + " was successfully added.");
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    System.out.println("###############################");
  }

  public void removeStorage() {

  }

  public void removeIngredient() {

  }


  public void app() {
    System.out.println("""
        Thank you for using the meal planning app! Earth thank you for taking care of her.
        Welcome new user""");
    userSetup(inputScanner);
    boolean appRunning = true;
    while (appRunning) {
      listMenu();
      switch (getUserInput()) {
        case 1 -> listStorages();
        case 4 -> addStorage();
        case 0 -> {
          System.out.println("Program Exiting...");
          appRunning = false;
        }
        default -> System.out.println("Sorry, we don't have option: " + getUserInput() + ", please try again.");
      }
    }
  }

  public static void main(String[] args) {
    Main user = new Main();
    user.app();
  }
}
