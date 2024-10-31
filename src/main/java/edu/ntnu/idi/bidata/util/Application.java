package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-10-31
 */
public class Application {

  User user;
  InputScanner inputScanner;
  OutputHandler outputHandler;

  public Application() {
    user = new User("testUser");
    inputScanner = new InputScanner();
    outputHandler = new OutputHandler();
  }

  /**
   * Starts the application by displaying the help string and entering a loop to process user commands.
   * The loop continues until a command causes the application to exit.
   * It handles any exceptions by printing the error messages.
   */
  public void startApplication() {
    outputHandler.printWelcomeMessage();
    outputHandler.printHelpMessage();
    engine();
  }

  /**
   * Runs the main loop of the application, continually processing user commands until
   * a termination command is received. The method fetches user input through an input scanner,
   * processes the command, and prints any exceptions encountered.
   */
  public void engine() {
    boolean running = true;
    do {
      try {
        UserInput userInput = inputScanner.getCommand();
        running = processUserCommand(userInput);
      } catch (Exception e) {
        outputHandler.printOutput(e.getMessage());
      }
    } while (running);
  }

  /**
   * Loop until get a valid input from the user.
   * Prints exception.
   *
   * @return none empty String.
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

  /**
   * Set up the current
   *
   * @return the newly created user object.
   */
  private User userSetUp() {
    return new User(getString().replaceAll("\\s", ""));
  }

  /**
   * Processes the user command and redirects to the appropriate function based on the command word.
   * Catches and handles IllegalArgumentExceptions that may occur during execution.
   *
   * @param userInput The command entered by the user.
   * @return The running condition of the application. Returning false will cause the application to exit.
   */
  private boolean processUserCommand(UserInput userInput) {
    boolean running = true;

    ValidCommand validCommand = userInput.getCommandWord();
    try {
      switch (validCommand) {
        case UNKNOWN -> System.out.println("I don't know what you mean...");
        case EXIT -> running = exit();
        case GO -> go(userInput);
        case HELP -> help(userInput);
        case LIST -> list(userInput);
        case ADD -> add(userInput);
        default -> throw new IllegalArgumentException("Unexpected command: " + validCommand);
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return running;
  }

  /**
   * @param userInput
   */
  private void add(UserInput userInput) {
    switch (userInput.getSubcommand()) {
      case null -> outputHandler.printOutput("Add what?");
      case "storage" -> {
        outputHandler.printOutputWithLineBreak("Please enter the name of new storage:");
        String storageName = getString();
        boolean success = user.addStorage(user.getCurrentDirectory().toString(), storageName);
        if (success) {
          System.out.println(storageName + " was successfully added.");
        } else {
          System.out.println("Failed to add " + storageName + " to the inventory.");
          System.out.println("failed to add new item");
        }
      }
      default -> throw new IllegalArgumentException("Unexpected subcommand: " + userInput.getSubcommand());
    }
  }

  /**
   * Lists items based on the given subcommand of the provided command.
   *
   * @param userInput The command entered by the user, containing a subcommand.
   */
  private void list(UserInput userInput) {
    String subcommand = userInput.getSubcommand();
    switch (subcommand) {
      case "inventory" -> outputHandler.printOutputWithLineBreak("tets");
      default -> throw new IllegalArgumentException("Unexpected command:  list " + subcommand);
    }
  }

  /**
   * Executes the "go" command, printing a message if no subcommand is provided.
   *
   * @param userInput The command entered by the user.
   */
  private void go(UserInput userInput) {
    user.go(userInput);
  }

  /**
   * Provides help messages to the user based on the given subcommand.
   *
   * @param userInput The command entered by the user, which may contain a subcommand.
   */
  private void help(UserInput userInput) {
    String subcommand = userInput.getSubcommand();
    if (subcommand == null) {
      outputHandler.printHelpMessage();
    } else {
      outputHandler.printHelpMessage(subcommand);
    }
  }

  /**
   * Method to exit the endless loop.
   *
   * @return the running condition of the application will cause the app to exit.
   */
  private boolean exit() {
    outputHandler.printGoodbyeMessage();
    return false;
  }

}




