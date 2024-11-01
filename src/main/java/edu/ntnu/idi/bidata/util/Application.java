package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-11-01
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
        UserInput userInput = inputScanner.readUserInput();
        running = processUserCommand(userInput);
      } catch (IllegalArgumentException e) {
        outputHandler.printOutput(e.getMessage());
      }
    } while (running);
  }

  /**
   * Set up the current
   *
   * @return the newly created user object.
   */
  private User userSetUp() {
    return new User(inputScanner.getUserInput().replaceAll("\\s", ""));
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
    switch (validCommand) {
      case UNKNOWN -> processUnknownCommand();
      case GO -> processGoCommand(userInput);
      case HELP -> processHelpCommand(userInput);
      case LIST -> processListCommand(userInput);
      case ADD -> processAddCommand(userInput);
      case EXIT -> running = exitApplication();
      default -> throw new IllegalArgumentException("Unexpected command: " + validCommand);
    }
    return running;
  }

  private void processUnknownCommand() {
    outputHandler.printOutput("I don't know what you mean...");
  }

  /**
   * @param userInput
   */
  private void processAddCommand(UserInput userInput) {
    switch (userInput.getSubcommand().toLowerCase()) {
      case null -> outputHandler.printOutput("Add what?");
      case "storage" -> addStorage();
      default -> throw new IllegalArgumentException("Unexpected subcommand: " + userInput.getSubcommand());
    }
  }

  private void addStorage() {
    outputHandler.printAddInstructions();
    String storageName = inputScanner.getUserInput();
    user.addStorage(storageName);
  }

  /**
   * Lists items based on the given subcommand of the provided command.
   *
   * @param userInput The command entered by the user, containing a subcommand.
   */
  private void processListCommand(UserInput userInput) {
    if (!userInput.hasSubcommand()) {
      if (user.isInDirectory()) {
        outputHandler.printOutputWithLineBreak(user.listInventory());
      }
    } else {
      String subcommand = userInput.getSubcommand();
      if (subcommand.equals("inventory")) {
        outputHandler.printOutputWithLineBreak("tets");
      } else {
        throw new IllegalArgumentException("Unexpected command:  list " + subcommand);
      }
    }
  }

  /**
   * Executes the "go" command, printing a message if no subcommand is provided.
   *
   * @param userInput The command entered by the user.
   */
  private void processGoCommand(UserInput userInput) {
    // TODO need to print when only write go
    if (!userInput.hasSubcommand()) {
      outputHandler.printHelpMessage(userInput.getCommandWord());
    }
    String directoryName = userInput.getSubcommand();
    user.goDir(directoryName);
  }

  /**
   * Provides help messages to the user based on the given subcommand.
   *
   * @param userInput The command entered by the user, which may contain a subcommand.
   */
  private void processHelpCommand(UserInput userInput) {
    String subcommand = userInput.getSubcommand();
    if (subcommand == null) {
      outputHandler.printHelpMessage();
    } else {
      outputHandler.printHelpMessage(userInput.getCommandWord());
    }
  }

  /**
   * Method to exit the endless loop.
   *
   * @return the running condition of the application will cause the app to exit.
   */
  private boolean exitApplication() {
    outputHandler.printGoodbyeMessage();
    return false;
  }

}




