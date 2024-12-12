package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;

/**
 * The HelpCommand class extends the Command class and is responsible for processing help-related commands.
 * This command provides help messages based on the presence of a subcommand or the main command itself.
 *
 * @author Nick Heggø
 * @version 2024-12-12
 */
public class HelpCommand extends Command {

  /**
   * Constructs a new HelpCommand object for the specified user.
   * The HelpCommand class is responsible for processing help-related commands.
   *
   * @param user The user for whom the help command is being created and processed.
   */
  public HelpCommand(User user) {
    super(user);
  }

  public HelpCommand(User user, ValidCommand command) {
    super(user);
    getOutputHandler().printCommandHelpMessage(command);
  }


  /**
   * Processes the subcommand for the HelpCommand. If a subcommand is present in the user's input,
   * it prints the specific help message for that subcommand. Otherwise, it prints the general help message.
   */
  @Override
  public void execute() {
    if (hasSubcommand()) {
      String subcommandInput = getSubcommand();
      getOutputHandler().printCommandHelpMessage(CommandRegistry.findCommand(subcommandInput));
    } else {
      getOutputHandler().printHelpMessage();
      getOutputHandler().printOutput("Additionally, try 'help' + {command}");
    }
  }

}
