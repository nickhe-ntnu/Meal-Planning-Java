package edu.ntnu.idi.bidata.util;

/**
 * The OutputHandler class is responsible for displaying various messages
 * to the user, including a welcome message that encourages environmental responsibility.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
public class OutputHandler {

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
    printOutput("""
        Thank you for using the meal planning app!
        Earth thanks you for taking care of her.""");
  }

  /**
   * Prints a farewell message to the user indicating the application
   * has ended and thanking them for usage.
   */
  public void printGoodbyeMessage() {
    printOutput("Thank you for using the application, goodbye!");
  }

  public void printOperationFailedMessage(String operation, String name) {
    printOutputWithLineBreak("Failed to " + operation + " " + name);
  }

  public void printOperationSuccessMessage(String operation, String name) {
    printOutputWithLineBreak("Successfully " + operation + " " + name);
  }

  public void printHelpMessage() {
    printOutput("""
        Available commands are:
          help, list, go, add, remove, find, exit""");

  }

  public void printHelpMessage(String subcommand) {
    switch (subcommand) {
      case "unknown" -> printUnknownInstruction();
      case "help" -> printHelpMessage();
      case "list" -> printListInstruction();
      case "go" -> printGoInstruction();
      case "add" -> printAddInstruction();
      case "remove" -> printRemoveInstruction();
      case "find" -> printFindInstruction();
      case "exit" -> printOutput("This command will terminate the application.");
      default -> printHelpInstruction();
    }
  }

  public void printUnknownInstruction() {
    printOutput("Unknown command, see 'help'");
  }

  private void printHelpInstruction() {
    printOutput("""
        Valid print commands are:
          help, help {valid command}""");
  }

  private void printListInstruction() {
    printOutput("""
        Valid list commands are:
          list inventory, list recipe, list ingredient,
          list expired, list available recipe""");

  }

  private void printGoInstruction() {
    printOutput("""
        Valid go commands are:
          go to {storage name}, go back""");
  }

  private void printAddInstruction() {
    printOutput("""
        Valid add commands are:
          add storage, add ingredient, add recipe""");
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
          find ingredient {ingredient name}, find recipe {recipe name}""");
  }

}
