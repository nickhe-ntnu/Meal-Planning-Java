package edu.ntnu.idi.bidata.util;

import java.util.HashMap;

public class SubCommandRegistry {
  HashMap<ValidCommand, String> validSubcommand;
  ValidCommand command;

  public SubCommandRegistry(ValidCommand command) {
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
