package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.util.command.Command;
import edu.ntnu.idi.bidata.util.command.ValidCommand;

public class SimpleApplication extends Application {

  protected SimpleApplication(User user) {
    super(user);
  }

  @Override
  protected Command getCommand() {
    // outputHandler.printCommandPrompt();
    // int input = inputScanner.collectValidInteger();
    // user.setCommandInput();
    // new CommandInput(CommandRegistry.getCommand(input));
    // return Command.of();
    return Command.of(ValidCommand.HELP, this);
  }
}
