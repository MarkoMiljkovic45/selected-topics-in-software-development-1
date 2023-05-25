package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.env.Environment;
import hr.fer.zemris.java.hw06.shell.env.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Util;

import java.util.List;
import java.util.Set;

/**
 * If started with no arguments, lists names of all supported commands.
 * <p>
 * If started with single argument, prints name and the description of selected command (or prints
 * appropriate error message if no such command exists).
 *
 * @author Marko Miljković (miljkovicmarko45@gmail.com)
 */
public class HelpShellCommand implements ShellCommand {
    public HelpShellCommand() {
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.parse(arguments);

        if (args == null) {
            Set<String> commandNames = env.commands().keySet();

            commandNames.forEach(env::writeln);
        } else {
            String commandName = args.get(0);

            ShellCommand command = env.commands().get(commandName);

            if (command == null) {
                throw new IllegalArgumentException("Command " + commandName + " doesn't exist.");
            }

            command.getCommandDescription().forEach(env::writeln);
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of(new String[]
                {"help - Gives command description\n",
                 "help [COMMAND]\n",
                 "COMMAND - Command name.",
                 "          If COMMAND is not provided lists names of all supported commands."}
        );
    }
}
