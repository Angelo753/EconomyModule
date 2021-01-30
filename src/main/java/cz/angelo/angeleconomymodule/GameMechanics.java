package cz.angelo.angeleconomymodule;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameMechanics implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		GameManager.registerPlayer(player);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		GameManager.unregisterPlayer(player);
	}

}
