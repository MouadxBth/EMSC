package me.espada.system;

import me.espada.Core;
import me.espada.system.utils.LogMessage;
import org.bukkit.command.CommandSender;

public class CoreLogger {


    private Core core;

    public CoreLogger(Core core) {
        this.core = core;
    }

    public void log(String content) {
        log(new LogMessage(core.getServer().getConsoleSender(), content));
    }

    public void log(String content, LogMessage.LogType type) {
        log(new LogMessage(core.getServer().getConsoleSender(), content, type));
    }

    public void log(String content, boolean save) {
        log(new LogMessage(core.getServer().getConsoleSender(), content), save);
    }

    public void log(CommandSender sender, String content) {
        log(new LogMessage(sender, content));
    }

    public void log(CommandSender sender, String content, LogMessage.LogType type) {
        log(new LogMessage(sender, content, type));
    }

    public void log(CommandSender sender, String content, boolean save) {
        log(new LogMessage(sender, content), save);
    }

    public void log(CommandSender sender, String content, LogMessage.LogType type, boolean save) {
        log(new LogMessage(sender, content, type), save);
    }

    public void log(LogMessage message) {
        log(message, false);
    }

    public void log(LogMessage message, boolean save) {
        core.getServer().getConsoleSender().sendMessage(new String[] {
                "",
                message.format(),
        });
        if(save | message.getLogType() == LogMessage.LogType.FATAL | message.getLogType() == LogMessage.LogType.ERROR) message.save();
    }

}
