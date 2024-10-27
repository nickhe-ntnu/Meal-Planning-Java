package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;

/**
 * The main starting point of your application. Let this class create the
 * instance of your main-class that starts your application.
 * @author Nick Heggø
 * @version 0.0.1
 */
public class Application {
  InputScanner inputScanner;
  User user;

  public Application() {
    inputScanner = new InputScanner();
    user = new User("test");
    user.addStorage("Refrigerator");
  }

  /**
   * Print welcome text
   */
  private void printWelcome() {
    System.out.println("""
        Thank you for using the meal planning app!
        Earth thanks you for taking care of her.""");
  }

  /**
   * Print output with custom linebreak
   */
  private void printOutput(Runnable task) {
    task.run();
    System.out.println("########################");
  }

  /**
   * Method to try - catch error
   * Print exceptions
   *
   * @return valid input
   */
  private String getString() {
    StringBuilder stringBuilder = new StringBuilder();

    boolean validInput = false;
    while (!validInput) {
      try {
        stringBuilder.append(inputScanner.getString());
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    return stringBuilder.toString();
  }

  private User userSetUp() {
    return new User(getString().replaceAll("\\s", ""));
  }

  private boolean processCommand(Command command) {
    boolean running = true;

    ValidCommand validCommand = command.getCommandWord();
    try {
      switch (validCommand) {
        case UNKNOWN -> System.out.println("I don't know what you mean...");
        case EXIT -> running = exit();
        case GO -> go(command);
        case HELP -> help(command);
        case LIST -> list(command);
        case ADD -> add(command);
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return running;
  }

  private void add(Command command) {
    switch (command.getSubcommand()) {
      case null -> System.out.println();
      case "storage" -> {
        printOutput(() -> {
          System.out.println("Please enter the name of new storage:");
          String storageName = getString();
          boolean success = user.addStorage(storageName);
          if (success) {
            System.out.println(storageName + " was successfully added.");
          } else {
            System.out.println("Failed to add " + storageName + " to the inventory.");
          }
        });
      }
      default -> throw new IllegalArgumentException("Unexpected value: " + command.getSubcommand());
    }
  }

  private boolean exit() {
    System.out.println("Thank you for using the app, goodbye!");
    return false;
  }

  private void list(Command command) {
    String subcommand = command.getSubcommand();
    switch (subcommand) {
      case null -> System.out.println("List what?");
      case "inventory" -> printOutput(() -> System.out.println(user.getInventoryString()));
      default -> throw new IllegalArgumentException("Unexpected value: " + subcommand);
    }
  }

  private void go(Command command) {
    if (!command.hasSubcommand()) {
      System.out.println("Where we going?");
    }
  }

  private void help(Command command) {
    String subcommand = command.getSubcommand();
    switch (subcommand) {
      case null -> printOutput(() -> System.out.println(inputScanner.getCommandString()));
      case "list" -> printOutput(() -> System.out.println("list inventory, list cookbook, list expired, "
          + "list recipe, list value"));

      default -> throw new IllegalArgumentException("Unexpected value: " + subcommand);
    }
  }

  public void run() {
    printWelcome();
    boolean running = true;
    while (running) {
      Command command = inputScanner.getCommand();
      running = processCommand(command);
    }
  }
}
