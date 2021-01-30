package cz.angelo.angeleconomymodule;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI  extends PlaceholderExpansion {

	Main instance;

	public PlaceholderAPI(Main instance){
		this.instance = instance;
	}

	@Override
	public boolean canRegister(){
		return true;
	}

	@Override
	public String getIdentifier() {
		return "aeconomy";
	}

	@Override
	public String getAuthor() {
		return "Angelo753";
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public String onRequest(OfflinePlayer player, String identifier) {
		if (identifier.equals("ppg")) {
			return String.valueOf(instance.gPlayer.get(player.getUniqueId()).getCurrency());
		}

		return null;
	}
}
