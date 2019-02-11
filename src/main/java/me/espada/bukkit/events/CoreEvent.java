package me.espada.bukkit.events;

import me.espada.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.lang.reflect.ParameterizedType;

public abstract class CoreEvent<T extends Event> {

    private EventPriority priority;
    private Core core = Core.getInstance();


    public void onLoad() {
        setPriority(EventPriority.NORMAL);
    }

    public void register() {
        Class<T>  clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        Bukkit.getServer().getPluginManager().registerEvent(clazz, new Listener() {}, priority, ($, rawEvent) -> {
            if (clazz.isAssignableFrom(rawEvent.getClass()))
                onExecute((T) rawEvent);
        }, core);
    }

    public abstract void onExecute(T event);

    public void setPriority(EventPriority priority) {
        this.priority = priority;
    }

    public Core getCore() {
        return core;
    }
}
