package edu.ntnu.idi.bidata.util;

public class Command {
  private ValidCommand command;
  private String subcommand;

  public Command(ValidCommand command, String subcommand) {
    this.command = command;
    this.subcommand = subcommand;
  }

  public ValidCommand getCommandWord() {
    return command;
  }

  public String getSubcommand() {
    return subcommand;
  }

  public boolean isUnknown() {
    return command == ValidCommand.UNKNOWN;
  }

  public boolean hasSubcommand() {
    return subcommand != null;
  }
}
