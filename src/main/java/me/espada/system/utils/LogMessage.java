package me.espada.system.utils;

import me.espada.Core;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class LogMessage {

    public enum LogType {
        INFO("{&bINFO&7}"), ERROR("{&cERROR&7}"), FATAL("{&4FATAL&7}");

        private String prefix;

        LogType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    private CommandSender sender;
    private String content;
    private LogType logType;
    private Config logFile;

    public LogMessage(CommandSender sender, String content, LogType logType) {
        this.sender = sender;
        this.content = content;
        this.logType = logType;
    }

    public LogMessage(CommandSender sender, String content) {
        this.sender = sender;
        this.content = content;
        this.logType = LogType.INFO;
    }


    public CommandSender getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LogType getLogType() {
        return logType;
    }

    public String format() {
        return Utils.colorize(sender.getName() + ": " + logType.getPrefix() + " " + content);
    }

    public void save() {
        final String path = Core.getInstance().getDataFolder().getAbsolutePath() + "/logs";

        logFile = new Config(path, "Log-" + Utils.getFilesFrom(path).size());
        logFile.setValue("DATE", new Date().toString());
        logFile.setValue("Sender", sender.getName());
        logFile.setValue("LogType", logType.toString());
        logFile.setValue("Content", content);
        logFile.save();
    }
}
