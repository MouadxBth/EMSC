package me.espada;

import me.espada.system.CoreLogger;
import me.espada.system.components.manager.ComponentsManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {

    private static Core instance;
    private static CoreLogger coreLogger;
    private ComponentsManager componentsManager;

    @Override
    public void onEnable() {
        instance = this;
        coreLogger = new CoreLogger(instance);
        componentsManager = new ComponentsManager();
        componentsManager.load.accept(instance);
    }

    @Override
    public void onDisable() {
        componentsManager.unload.accept(instance);
        coreLogger = null;
        instance = null;
    }

    public File getJarFile() {
        return getFile();
    }

    public static Core getInstance() {
        return instance;
    }

    public static CoreLogger getCoreLogger() {
        return coreLogger;
    }
}
