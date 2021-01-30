package cz.angelo.angeleconomymodule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

	public static Main instance;
	public HashMap<UUID, GamePlayer> gPlayer = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;
		this.getCommand("ppg").setExecutor(new Commands());
		getCommand("ppg").setTabCompleter(new Commands());
		this.getServer().getPluginManager().registerEvents(new GameMechanics(), this);
		new PlaceholderAPI(this).register();
		for (Player player : Bukkit.getOnlinePlayers()){
			GameManager.registerPlayer(player);
		}
	}

	@Override
	public void onDisable() {
		for (UUID uuid : this.gPlayer.keySet()){
			int currency = this.gPlayer.get(uuid).getCurrency();
			Config.get().set("data." + uuid, currency);
			Config.save();
		}
	}


	public String color(String string){
		return ChatColor.translateAlternateColorCodes('&', string);
	}



}
