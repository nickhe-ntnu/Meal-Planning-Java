package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.command.*;
import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.UserInput;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-11-02
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
  public void initialize() {
    outputHandler.printWelcomeMessage();
    outputHandler.printHelpMessage();
    engine();
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
   * Runs the main loop of the application, continually processing user commands until
   * a termination command is received. The method fetches user input through an input scanner,
   * processes the command, and prints any exceptions encountered.
   */
  public void engine() {
    boolean running = true;
    do {
      try {
        this.user.setUserInput(inputScanner.fetchUserInput());
        running = processUserCommand(user.getUserInput());
      } catch (IllegalArgumentException e) {
        outputHandler.printOutput(e.getMessage());
      }
    } while (running);
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
      case UNKNOWN -> processUnknownCommand(userInput);
      case HELP -> new HelpCommand(this.user);
      case LIST -> new ListCommand(this.user);
      case GO -> new GoCommand(this.user);
      case ADD -> new AddCommand(this.user);
      case REMOVE -> new RemoveCommand(this.user);
      case FIND -> new FindCommand(this.user);
      case EXIT -> running = exitApplication();
      default -> throw new IllegalArgumentException("Unexpected command: " + validCommand);
    }
    return running;
  }

  /**
   * Processes an unknown command by printing a help message specific to the command word.
   *
   * @param userInput The command entered by the user.
   */
  private void processUnknownCommand(UserInput userInput) {
    outputHandler.printHelpMessage(userInput.getCommandWord().name().toLowerCase());
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




