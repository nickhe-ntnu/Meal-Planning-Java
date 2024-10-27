package edu.ntnu.idi.bidata.util;

import java.util.Scanner;

public class InputScanner {
  private UnitTypes unitTypes;
  private CommandWord commands;
  private Scanner scanner;

  /**
   * Create a input scanner to read from the terminal window.
   */
  public InputScanner() {
    scanner = new Scanner(System.in);
    commands = new CommandWord();
    unitTypes = new UnitTypes();
  }

  public String getString() {
    System.out.print("> ");
    String inputLine = scanner.nextLine().trim();
    if (inputLine.isBlank()) {
      throw new IllegalArgumentException("Input cannot be blank.");
    }
    return inputLine;
  }

  public Command getCommand() {
    System.out.print("> ");
    String inputLine = scanner.nextLine().toLowerCase();
    String command = null;
    String subcommand = null;

    Scanner tokenizer = new Scanner(inputLine);
    if (tokenizer.hasNext()) {
      command = tokenizer.next();
      if (tokenizer.hasNext()) {
        subcommand = tokenizer.next();
      }
    }
    return new Command(commands.getCommandWord(command), subcommand);
  }

  public String getCommandString() {
    return commands.getCommandString();
  }
}
