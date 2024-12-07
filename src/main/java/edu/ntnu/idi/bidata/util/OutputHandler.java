package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.util.command.ValidCommand;

import java.util.List;

/**
 * The OutputHandler class is responsible for displaying various messages
 * to the user, including a welcome message that encourages environmental responsibility.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class OutputHandler {

  private static final String WELCOME_MESSAGE = """
      Hello, %s
      Thank you for using the meal planning app!""";

  public OutputHandler() {
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
    System.out.print(inputPrompt);
    System.out.println(" Type 'abort' to abort the operation.");
    System.out.print("  ~ ");
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
    switch (command) {
      case UNKNOWN -> printUnknownInstruction();
      case HELP -> printHelpMessage();
      case LIST -> printListInstruction();
      case GO -> printGoInstruction();
      case ADD -> printAddInstruction();
      case REMOVE -> printRemoveInstruction();
      case FIND -> printFindInstruction();
      case EXIT -> printOutput("This command will terminate the application.");
      default -> printHelpInstruction();
    }
  }

  public void printHelpMessage() {
    printOutput("Available commands are:" + "\n" + formatCommands());
  }

  public void printUnknownInstruction() {
    printOutput("Unknown command, see 'help'");
  }

  public void clearScreen() {
    for (int i = 0; i < 40; i++) {
      System.out.println("");
    }
  }

  private String formatCommands() {
    List<String> commands = ValidCommand.getCommands();
    return " help | " + String.join(" | ", commands) + " | clear | exit";
  }

  private void printHelpInstruction() {
    printOutput("""
        Valid print commands are:
         help | help {valid command}""");
  }

  private void printListInstruction() {
    printOutput("""
        Valid list commands are:
         list inventory | list recipe | list ingredient
         list expired | list available recipe""");

  }

  private void printGoInstruction() {
    printOutput("""
        Valid go commands are:
         go to {storage name} | go back""");
  }

  private void printAddInstruction() {
    printOutput("""
        Valid add commands are:
         add storage | add ingredient | add recipe""");
  }

  private void printRemoveInstruction() {
    printOutput("""
        Valid remove commands are:
         remove storage {storage name}
         remove ingredient {ingredient name}
         remove recipe {recipe name}""");
  }

  private void printFindInstruction() {
    printOutput("""
        Valid find commands are:
         find ingredient {ingredient name} | find recipe {recipe name}""");
  }

}
