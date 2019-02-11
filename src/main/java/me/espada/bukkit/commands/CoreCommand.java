package me.espada.bukkit.commands;

import me.espada.Core;
import me.espada.system.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class CoreCommand extends Command implements TabCompleter {

    private String name, description, usageMessage, permission;
    private boolean consoleOnly;
    private List<String> aliases;
    private Core core = Core.getInstance();

    public CoreCommand(String name, String description, String usageMessage, String permission,boolean consoleOnly,  List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.consoleOnly = consoleOnly;
        this.name = name;
        this.description = description;
        this.usageMessage = usageMessage;
        this.permission = permission;
        this.aliases = aliases;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        Function<CommandSender, Boolean> executeFunction = (cs) -> {
            Predicate<CommandSender> isPlayer = (commandSender) -> commandSender instanceof Player;

            BiFunction<Supplier<CommandSender>, Boolean, Boolean> commandBiFunction =
                    (sp, b) -> b ? (sp.get().hasPermission("core.overpowerd") ? preExecute(sp.get(), s, args) : rejected(sp.get()))
                            : (sp.get().hasPermission(permission) ? preExecute(sp.get(), s, args) : noPermission(sp.get()));

            return commandBiFunction.apply(()-> sender, isConsoleOnly());
        };

        return executeFunction.apply(sender);

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private boolean preExecute(CommandSender sender, String s, String[] args ) {
        onExecute(sender, s, args);
        return true;
    }

    private boolean rejected(CommandSender sender) {
        Core.getCoreLogger().log("The player &7" + sender.getName() + " &4Tried to use a Console only command!", true);
        return false;
    }

    public boolean noPermission(CommandSender sender) {
        sender.sendMessage(Utils.colorize("&cYou don't have permission to execute that command!"));
        return false;
    }

    public abstract void onExecute(CommandSender sender, String command, String[] args);

    public void register() {
        try {
            SimplePluginManager pluginManager = (SimplePluginManager) Core.getInstance().getServer().getPluginManager();
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            SimpleCommandMap map = (SimpleCommandMap) field.get(pluginManager);
            map.register("Core", this);

            onRegister();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void unregister() {

        Object result = Utils.getField(Core.getInstance().getServer().getPluginManager(), "commandMap");
        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        Object map = Utils.getField(commandMap, "knownCommands");

        HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;

        knownCommands.remove(getName());

        getAliases().forEach(knownCommands::remove);

        onUnregister();

    }

    public abstract void onRegister();

    public abstract void onUnregister();


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getUsageMessage() {
        return usageMessage;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    public boolean isConsoleOnly() {
        return consoleOnly;
    }

    public void setCName(String name) {
        this.name = name;
    }

    public void setCDescription(String description) {
        this.description = description;
    }

    public void setCUsageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
    }


    public void setCPermission(String permission) {
        this.permission = permission;
    }

    public void setConsoleOnly(boolean consoleOnly) {
        this.consoleOnly = consoleOnly;
    }

    public void setCAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public Core getCore() {
        return core;
    }
}
