package edu.ntnu.idi.bidata.util;

/**
 * The OutputHandler class is responsible for displaying various messages
 * to the user, including a welcome message that encourages environmental responsibility.
 *
 * @author Nick Heggø
 * @version 2024-11-01
 */
public class OutputHandler {

  public OutputHandler() {}

  /**
   * Prints the specified message to the console and follows it with a line of separator characters.
   *
   * @param outputMessage the message to be printed to the console
   */
  public void printOutputWithLineBreak(String outputMessage) {
    printOutput(outputMessage);
    System.out.println("########################");
  }

  /**
   * Prints the specified output to the console.
   *
   * @param output the message to be printed to the console
   */
  public void printOutput(String output) {
    System.out.println(output);
  }

  /**
   * Prints a welcome message to the user indicating the application has started
   * and encourages environmental responsibility.
   */
  public void printWelcomeMessage() {
    System.out.println("""
        Thank you for using the meal planning app!
        Earth thanks you for taking care of her.""");
  }

  /**
   * Prints a farewell message to the user indicating the application
   * has ended and thanking them for usage.
   */
  public void printGoodbyeMessage() {
    System.out.println("Thank you for using the app, goodbye!");
  }


  public void printHelpMessage() {
    String helpString = "";
  }
  public void printHelpMessage(ValidCommand command) {

  }

  public void printAddInstructions() {
    System.out.println("""
        """);
  }


}
