package me.espada.bukkit.events.manager;

import me.espada.Core;
import me.espada.bukkit.events.CoreEvent;
import me.espada.system.components.Component;
import me.espada.system.components.utils.ComponentPriority;
import me.espada.system.utils.Utils;
import org.bukkit.event.HandlerList;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventsManager extends Component {


    public void load() {
        setComponentPriority(ComponentPriority.NORMAL);
    }

    @Override
    public boolean enable() {
       registerEvents.accept(Utils.getClasses(Core.getInstance().getJarFile(), "me.espada", CoreEvent.class));
        return true;
    }

    @Override
    public boolean disable() {
        HandlerList.unregisterAll(Core.getInstance());
        Core.getCoreLogger().log("&2All events were unregistred!");
        return true;
    }

    private Predicate<Collection<Class<?>>> verifyClasses = (c) -> c != null && !c.isEmpty();

    private Consumer<Collection<Class<?>>> registerEvents = (c) -> {
        if(!verifyClasses.test(c)) return;
        c.forEach(cz -> {
            try {
                CoreEvent event = (CoreEvent) cz.newInstance();
                event.onLoad();
                event.register();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        });

        Core.getCoreLogger().log("&2All events were registred!");

    };


}
