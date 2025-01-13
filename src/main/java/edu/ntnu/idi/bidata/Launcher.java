package edu.ntnu.idi.bidata;

import java.util.Scanner;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.util.Utility;

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

  private static boolean isAdvancedModeOn() {
    String input;
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Do you wish to initialize the advanced mode? (Y/n)");
      input = scanner.nextLine();
    }
    return Utility.isContinuationConfirmed(input);
  }

  /**
   * The main entry point of the application. Instantiates and runs the Application.
   */
  public static void main(String[] args) {
    Application app;
    User user = new User();
    if (isAdvancedModeOn()) {
      app = new AdvancedApplication(user);
    } else {
      app = new SimpleApplication(user);
    }
    app.run();
  }

}
