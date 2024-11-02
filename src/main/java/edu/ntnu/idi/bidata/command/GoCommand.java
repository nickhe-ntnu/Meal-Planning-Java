package edu.ntnu.idi.bidata.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * Represents a command to navigate to different locations or storages, or to go back
 * to the previous directory for a user.
 *
 * @author Nick Heggø
 * @version 2024-11-02
 */
public class GoCommand extends Command {

  /**
   * Constructs a new GoCommand object for the specified user, enabling
   * the user to navigate through different locations, storages, or to
   * go back to the previous directory.
   *
   * @param user The user for whom the GoCommand is being created and processed.
   */
  public GoCommand(User user) {
    super(user);
  }

  /**
   * Processes the subcommand input by the user and delegates execution to
   * the appropriate method based on the subcommand value.
   *
   * The method interprets the following subcommands:
   * - "location": Calls the goLocation() method to handle navigation to a location.
   * - "storage": Calls the goStorage() method to handle navigation to storage.
   * - "back": Calls the goBack() method to handle navigation back to the previous directory.
   */
  @Override
  protected void processSubcommand() {
    switch (userInputSubcommand) {
      case "location" -> goLocation();
      case "storage" -> goStorage();
      case "back" -> goBack();
    }
  }

  private void goLocation() {
    // TODO
  }

  private void goStorage() {
    // TODO
  }

  private void goBack() {
    // TODO
  }
}
