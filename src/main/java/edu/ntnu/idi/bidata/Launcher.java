package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.user.User;

/**
 * The Launcher class serves as the entry point for the meal planning application.
 * It instantiates the Application class and starts the application.
 *
 * @author Nick Heggø
 * @version 2024-12-12
 */
public class Launcher {

  private Launcher() {
  }

  /**
   * The main entry point of the application. Instantiates and runs the Application.
   */
  public static void main(String[] args) {
    User user = new User();
    Application app = new AdvancedApplication(user);
    app.run();
  }

}
