package cz.upce.fei.boop.pujcovna.command;

public class CommandLineMain {

    public static void main(String[] args) {

        Reader reader = new Reader();
        CommandDecoder commandDecoder = new CommandDecoder();
        String command;

        do {

            command = reader.readString("\nNapis prikaz: ", "volba");
            commandDecoder.executeCommand(command);

        } while (!command.equals("exit"));

    }

}
