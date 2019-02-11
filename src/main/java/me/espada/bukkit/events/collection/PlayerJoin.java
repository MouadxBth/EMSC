package me.espada.bukkit.events.collection;

import me.espada.bukkit.events.CoreEvent;
import me.espada.system.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin extends CoreEvent<PlayerJoinEvent> {


    public void onLoad() {
        setPriority(EventPriority.HIGH);
    }

    @Override
    public void onExecute(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Utils.colorize("&6Welcome to MAO's SERVER!"));
    }

}
