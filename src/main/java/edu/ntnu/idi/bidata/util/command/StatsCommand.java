package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

public class StatsCommand extends Command {
  public StatsCommand(User user) {
    super(user);
  }

  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      default -> printStats();
    }
  }

  private void printStats() {
    //    inventoryManager.getInventoryValue();
  }

}
