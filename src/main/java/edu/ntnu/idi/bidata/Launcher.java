package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.util.Application;

/**
 * The Launcher class serves as the entry point for the meal planning application.
 * It instantiates the Application class and starts the application by calling its initialize method.
 *
 * @author Nick Heggø
 * @version 2024-12-07
 */
public class Launcher {

  private Launcher() {
  }

  public static void main(String[] args) {
    Application app = new Application();
    app.run();
  }

}
