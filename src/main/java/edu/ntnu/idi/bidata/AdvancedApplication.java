package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.util.command.Command;

/**
 * The Application class represents the main execution for the meal planning application.
 * It initializes user data, including storage, and manages user inputs to process commands.
 *
 * @author Nick Heggø
 * @version 2024-12-12
 */
public class AdvancedApplication extends Application{

  /**
   * Initializes a new instance of the Application
   * Sets up the user, input scanner, and output handler.
   */
  public AdvancedApplication(User user) {
    super(user);
  }

  @Override
  protected Command getCommand() {
    outputHandler.printCommandPrompt();
    user.setCommandInput(inputScanner.fetchCommand());
    return Command.of(user, this);
  }

}
