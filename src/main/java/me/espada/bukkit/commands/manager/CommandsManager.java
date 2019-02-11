package me.espada.bukkit.commands.manager;

import me.espada.Core;
import me.espada.bukkit.commands.CoreCommand;
import me.espada.system.components.Component;
import me.espada.system.components.utils.ComponentPriority;
import me.espada.system.utils.Utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommandsManager extends Component {

    private Set<CoreCommand> commands = new HashSet<>();

    public void load() {
        setComponentPriority(ComponentPriority.NORMAL);
    }

    @Override
    public boolean enable() {
        registerCommands.accept(Utils.getClasses(Core.getInstance().getJarFile(), "me.espada", CoreCommand.class));
        return true;
    }

    @Override
    public boolean disable() {
        if(commands.isEmpty()) return true;
        commands.forEach(CoreCommand::unregister);
        Core.getCoreLogger().log("&2All commands were unregistred!");
        return true;
    }


    private Predicate<Collection<Class<?>>> verifyClasses = (c) -> c != null && !c.isEmpty();

    private Consumer<Collection<Class<?>>> registerCommands = (c) -> {
        if (verifyClasses.test(c)) {
            c.forEach(cz -> {
                try {
                    CoreCommand coreCommand = (CoreCommand) cz.newInstance();
                    coreCommand.register();
                    commands.add(coreCommand);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            Core.getCoreLogger().log("&2All commands were registred!");
        }
    };

    public Set<CoreCommand> getCommands() {
        return commands;
    }
}
