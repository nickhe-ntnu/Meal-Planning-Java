package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.util.command.ValidCommand;

import java.util.List;

/**
 * The OutputHandler class is responsible for displaying various messages
 * to the user, including a welcome message that encourages environmental responsibility.
 *
 * @author Nick Heggø
 * @version 2024-12-08
 */
public class OutputHandler {

  private static final String WELCOME_MESSAGE = """
      Hello, %s
      Thank you for using the meal planning app!""";

  public OutputHandler() {
    // default implementation
  }

  /**
   * Prints the specified message to the console and follows it with a line of separator characters.
   *
   * @param outputMessage the message to be printed to the console
   */
  public void printOutputWithLineBreak(String outputMessage) {
    printOutput(outputMessage);
    System.out.println("########################");
  }

  public void printLineBreak() {
    System.out.println("########################");
  }

  public void printCommandPrompt() {
    System.out.print("> ");
  }

  public void printInputPrompt(String inputPrompt) {
    String output = inputPrompt + " Type 'abort' to abort the operation."
        + "\n" + "  ~ ";
    System.out.print(output);
  }

  public void printInputPrompt() {
    System.out.print("  ~ ");
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
  public void printWelcomeMessage(String userName) {
    printOutput(String.format(WELCOME_MESSAGE, userName));
  }

  /**
   * Prints a farewell message to the user to indicating the application has ended.
   */
  public void printGoodbyeMessage() {
    printOutput("Thank you for using the application, goodbye!");
  }

  /**
   * Prints a formatted message indicating the success or failure of an operation,
   * followed by a line of separator characters.
   *
   * @param success   a boolean value indicating if the operation was successful
   * @param operation the name of the operation performed
   * @param name      the name associated with the operation
   */
  public void printOperationStatus(boolean success, String operation, String name) {
    if (success) {
      printOutputWithLineBreak("Successfully " + operation + " " + name);
    } else {
      printOutputWithLineBreak("Failed to " + operation + " " + name);
    }
  }

  /**
   * Prints the items in the given list in a specified style format.
   *
   * @param listToPrint the list of items to be printed
   * @param style       the style format for printing: "bullet", "numbered", or "suffix"
   * @throws IllegalArgumentException if the list is empty or null
   */
  public void printList(List<?> listToPrint, String style) {
    if (listToPrint == null || listToPrint.isEmpty()) {
      throw new IllegalArgumentException("List is empty!");
    }

    if (style.strip().equalsIgnoreCase("bullet")) {
      for (Object o : listToPrint) {
        printOutput(" * " + o);
      }
    } else if (style.strip().equalsIgnoreCase("numbered")) {
      for (int index = 0; index < listToPrint.size(); index++) {
        printOutput(" #" + (index + 1) + ": " + listToPrint.get(index));
      }
    } else if (style.strip().equalsIgnoreCase("suffix")) {
      for (int index = 0; index < listToPrint.size(); index++) {
        printOutput(" " + (index + 1) + Utility.getOrdinalSuffix(index + 1) + ": " + listToPrint.get(index));
      }
    }
  }

  /**
   * Prints a help message corresponding to the given command.
   *
   * @param command the command for which the help message should be printed
   */
  public void printCommandHelpMessage(ValidCommand command) {
    printOutput(command.getHelpString());
  }

  public void printHelpMessage() {
    printOutput("Available commands are:" + "\n" + getHelpMessage());
  }

  public void clearScreen() {
    for (int i = 0; i < 40; i++) {
      System.out.println();
    }
  }

  private String getHelpMessage() {
    List<String> commands = ValidCommand.getCommands();
    return " help | " + String.join(" | ", commands) + " | clear | exit";
  }

}
