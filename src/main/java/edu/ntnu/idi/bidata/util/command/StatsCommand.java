package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The StatsCommand class extends the Command class and is responsible for
 * executing a command that prints user statistics.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class StatsCommand extends Command {
  public StatsCommand(User user) {
    super(user);
  }

  /**
   * Executes the stats command by invoking printStats to display user statistics.
   */
  @Override
  public void execute() {
    printStats();
  }

  /**
   * Prints user statistics related to inventory and other metrics.
   * Currently calls inventoryManager to get inventory value.
   */
  private void printStats() {
    //    inventoryManager.getInventoryValue();
  }

}
