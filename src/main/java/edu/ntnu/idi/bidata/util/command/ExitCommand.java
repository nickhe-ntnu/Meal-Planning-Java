package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.util.Application;

/**
 * The ExitCommand class is responsible for terminating the application
 * upon execution. It extends the Command class to provide a concrete
 * implementation of the execute method.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class ExitCommand extends Command {
  private Application app;

  public ExitCommand(User user, Application app) {
    super(user);
    this.app = app;
  }

  /**
   * Executes the exit command to terminate the application.
   * This method calls the terminate method on the application
   * instance, effectively stopping the application.
   */
  @Override
  public void execute() {
    app.terminate();
  }
}
