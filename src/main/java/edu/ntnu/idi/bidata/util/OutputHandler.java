package edu.ntnu.idi.bidata.util;

import java.lang.System;

/**
 * The OutputHandler class is responsible for displaying various messages
 * to the user, including a welcome message that encourages environmental responsibility.
 * @author Nick Heggø
 * @version 2024-10-30
 */
public class OutputHandler {

  public OutputHandler() {}

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
   * Executes the given task and prints a line break for separation.
   *
   * @param task the task to be executed before printing the line break
   */
  public void printOutputWithLineBreak(Runnable task) {
    task.run();
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
   * Prints a farewell message to the user indicating the application
   * has ended and thanking them for usage.
   */
  public void printGoodbyeMessage() {
    System.out.println("Thank you for using the app, goodbye!");
  }


  public void printHelpMessage() {

  }
  public void printHelpMessage(String command) {

  }


}
