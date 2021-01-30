package cz.angelo.angeleconomymodule;

import org.bukkit.entity.Player;

public class GamePlayer {

	private int currency;
	private Player player;

	public GamePlayer(Player player, int currency){
		this.player = player;
		this.currency = currency;
	}


	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public void addCurrency(int currency) {
		this.currency += currency;
	}

	public void removeCurrency(int currency) {
		if (!(this.currency < currency)) {
			this.currency -= currency;
		}
	}

}
