package me.espada.system.utils;

import me.espada.Core;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Set;

public class Config {


    private File f;
    private YamlConfiguration c;
    private String name;

    public Config(String filePath, String fileName) {
        name = fileName;
        this.f = new File(filePath, fileName + ".yml");
        this.c = YamlConfiguration.loadConfiguration(f);
    }

    public Config setValue(String valuePath, Object value) {
        this.c.set(valuePath, value);
        save();
        return this;
    }

    public Config set(String valuePath, Object value) {
        c.set(valuePath, value);
        save();
        return this;
    }

    public boolean exists() {
        return f.exists();
    }

    public Object getObject(String valuePath) {
        return c.get(valuePath);
    }

    public Integer getInt(String valuePath) {
        return c.getInt(valuePath);
    }

    public Double getDouble(String valuePath) {return c.getDouble(valuePath);}

    public float getFloat(String valuePath) {return (float)c.getDouble(valuePath);}

    public String getString(String valuePath) {
        return c.getString(valuePath);
    }

    public boolean getBoolean(String valuePath) {
        return c.getBoolean(valuePath);
    }

    public List<String> getStringList(String listPath) {
        return c.getStringList(listPath);
    }

    public Set<String> getKeys(boolean deep) {
        return c.getKeys(deep);
    }

    public File getFile() {
        return f;
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return c.getConfigurationSection(path);
    }

    public Config save() {
        try {
            c.save(f);
            return this;
        } catch (Exception e) {
            Core.getInstance().getCoreLogger().log(
                    Core.getInstance().getServer().getConsoleSender(),
                    "&cCould not save a file with the name: &7" + name + " printstack: " + e.getMessage(),
                    true );
            return null;
        }
    }

}
