package edu.ntnu.idi.bidata.util.command;

import java.util.HashMap;

/**
 * Manages a registry of subcommands associated with specific valid commands.
 * This class allows the addition of subcommands to a command and stores them
 * for later reference or validation.
 * <h>Currently not been implemented</h>
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
public class SubcommandRegistry {
  private HashMap<ValidCommand, String> validSubcommand;
  private ValidCommand command;

  public SubcommandRegistry(ValidCommand command) {
    this.command = command;
    validSubcommand = new HashMap<>();
  }

  public void addValidSubcommand(String subcommand) {
    validSubcommand.put(command, subcommand);
  }

  /*public String getValidSubcommand() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String subcommand : validSubcommand.)
  }*/
}
