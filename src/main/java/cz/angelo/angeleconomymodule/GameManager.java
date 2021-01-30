package cz.angelo.angeleconomymodule;

import org.bukkit.entity.Player;

import java.util.UUID;

public class GameManager {

	public static GameManager instance;

	public GameManager(){
		instance = this;
	}

	public static void registerPlayer(Player player){
		GamePlayer gamePlayer;
		if (Config.get().getString("data." + player.getUniqueId()) != null){
			gamePlayer = new GamePlayer(player, Config.get().getInt("data." + player.getUniqueId()));
			Main.instance.gPlayer.put(player.getUniqueId(), gamePlayer);
		}else {
			Config.get().set("data." + player.getUniqueId(), 0);
			gamePlayer = new GamePlayer(player, 0);
			Main.instance.gPlayer.put(player.getUniqueId(), gamePlayer);
			Config.save();
		}
	}

	public static void unregisterPlayer(Player player){
		UUID uuid = player.getUniqueId();
		int currency = Main.instance.gPlayer.get(uuid).getCurrency();
		Config.get().set("data." + uuid, currency);
		Config.save();
	}

	public static boolean playerOnline(Player player){
		if (player != null){
			return true;
		}else {
			player.sendMessage(Main.instance.color(Config.get().getString("messages.playerOffline")));
			return false;
		}
	}

	public static boolean isInt(String number){
		try {
			Integer.parseInt(number);
		}catch (NumberFormatException ex){

			return false;
		}
		return true;
	}

	public static boolean isInt(Player player, String number){
		try {
			Integer.parseInt(number);
		}catch (NumberFormatException ex){
			player.sendMessage(Main.instance.color(Config.get().getString("messages.wrongNumber")));
			return false;
		}
		return true;
	}

	public static boolean playerHasCurrency(Player player, String amount){
		if (isInt(amount)) {
			if (Config.get().getDouble("data." + player.getUniqueId()) >= Integer.parseInt(amount)) {
				return true;
			} else {
				player.sendMessage(Main.instance.color(Config.get().getString("messages.noMoney")));
				return false;
			}
		}
		return false;
	}

}
