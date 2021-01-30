package cz.angelo.angeleconomymodule;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

	private GameManager gmanager;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (args.length == 0){
				player.sendMessage(Main.instance.color(Config.get().getString("messages.balance")).replace("{AMOUNT}", String.valueOf(Main.instance.gPlayer.get(player.getUniqueId()).getCurrency())));
			}else if (args.length == 3){
				if (args[0].equalsIgnoreCase("give")) {
					if (player.hasPermission("*") || player.hasPermission("aeconomy.give")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (GameManager.playerOnline(target)) {
							if (GameManager.isInt(player, args[2])) {
								Main.instance.gPlayer.get(target.getUniqueId()).addCurrency(Integer.parseInt(args[2]));
								player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(player, Config.get().getString("messages.give"))).replace("{AMOUNT}", args[2]));
								player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(target, Config.get().getString("messages.giveTarget"))).replace("{AMOUNT}", args[2]));
							}
						}
					}else {
						player.sendMessage(Main.instance.color(Config.get().getString("messages.permissionError")));
					}
				}else if (args[0].equalsIgnoreCase("take")){
					if (player.hasPermission("*") || player.hasPermission("aeconomy.take")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (GameManager.playerOnline(target)) {
							if (GameManager.isInt(player, args[2])) {
								if (GameManager.playerHasCurrency(target, args[2])) {
									Main.instance.gPlayer.get(target.getUniqueId()).removeCurrency(Integer.parseInt(args[2]));
									player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(player, Config.get().getString("messages.take"))).replace("{AMOUNT}", args[2]));
									player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(target, Config.get().getString("messages.takeTarget"))).replace("{AMOUNT}", args[2]));
								}
							}
						}
					}else {
						player.sendMessage(Main.instance.color(Config.get().getString("messages.permissionError")));
					}
				} else if (args[0].equalsIgnoreCase("pay")) {
					if (player.hasPermission("*") || player.hasPermission("aeconomy.pay")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (GameManager.playerOnline(target)) {
							if (GameManager.isInt(player, args[2])) {
								if (GameManager.playerHasCurrency(player, args[2])) {
									Main.instance.gPlayer.get(target.getUniqueId()).addCurrency(Integer.parseInt(args[2]));
									Main.instance.gPlayer.get(player.getUniqueId()).removeCurrency(Integer.parseInt(args[2]));
									player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(player, Config.get().getString("messages.pay"))).replace("{AMOUNT}", args[2]));
									player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(target, Config.get().getString("messages.payTarget"))).replace("{AMOUNT}", args[2]));
								}
							}
						}
					}else {
						player.sendMessage(Main.instance.color(Config.get().getString("messages.permissionError")));
					}
				}else {
					player.sendMessage(Main.instance.color(Config.get().getString("messages.wrongUsage")));
				}
			}else if (args.length == 2){
				if (args[0].equalsIgnoreCase("reset")){
					if (player.hasPermission("*") || player.hasPermission("aeconomy.reset")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (GameManager.playerOnline(target)) {
							int reset = Config.get().getInt("currencies.ppg.startBalance");
							Main.instance.gPlayer.get(target.getUniqueId()).setCurrency(reset);
							player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(player, Config.get().getString("messages.reset")).replace("{AMOUNT}", String.valueOf(reset))));
							player.sendMessage(Main.instance.color(PlaceholderAPI.setPlaceholders(target, Config.get().getString("messages.ressetTarget"))).replace("{AMOUNT}", String.valueOf(reset)));
						}
					}else {
						player.sendMessage(Main.instance.color(Config.get().getString("messages.permissionError")));
					}
				}else {
					player.sendMessage(Main.instance.color(Config.get().getString("messages.wrongUsage")));
				}
			}else if (args.length == 1){
				if (args[0].equalsIgnoreCase("reload")){
					Config.reload();
					player.sendMessage(Main.instance.color(Config.get().getString("messages.reload")));
				}
			} else {
				player.sendMessage(Main.instance.color(Config.get().getString("messages.wrongUsage")));
			}
		}else {
			sender.sendMessage(Main.instance.color(Config.get().getString("messages.onlyPlayer")));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1){
			completions.add("give");
			completions.add("pay");
			completions.add("reload");
			completions.add("reset");
			completions.add("take");
		}else if (args.length == 2){
			return null;
		}else if (args.length == 3){
			for (int i = 1; i < 10; i++){
				completions.add(String.valueOf(i));
			}
		}else {
			completions.add(Main.instance.color(Config.get().getString("messages.wrongUsage")));
		}
		Collections.sort(completions);
		return completions;
	}

}
