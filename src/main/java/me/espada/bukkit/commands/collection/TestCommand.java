package me.espada.bukkit.commands.collection;

import me.espada.bukkit.commands.CoreCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class TestCommand extends CoreCommand {

    public TestCommand() {
        super("test", "cool test command", "voiala", "", false, Arrays.asList("t", "tr"));
    }

    @Override
    public void onExecute(CommandSender sender, String command, String[] args) {
        sender.sendMessage("FYUCK YOUIqjpqsd");
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUnregister() {

    }
}
