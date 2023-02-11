package ru.zoga_com.caulcrafting.commands;

import org.bukkit.entity.Player;

import ru.zoga_com.caulcrafting.CaulCrafting;
import ru.zoga_com.caulcrafting.CraftArray;
import ru.zoga_com.caulcrafting.CraftFormatting;
import ru.zoga_com.caulcrafting.Language;

import java.util.ArrayList;

public class RemoveCraftCommand {

	public static boolean removeCraftCommand(Player player, String[] args, CaulCrafting plugin) {
		if (!player.hasPermission("caulcrafting.admin.remove")) {
			player.sendMessage("§c" + Language.getTranslation("general_do_not_permission"));
			return false;
		}
		//Get craft to remove ID
		int nb = -1;
		if(args.length > 1){
			String p = args[1];
			try{
				nb = Integer.parseInt(p);
				if(nb < 0)
					nb = -1;
			}catch (Exception e){
				nb = -1;
			}
		}
		if (nb == -1) {
			player.sendMessage("§c" + Language.getTranslation("craftremove_error"));
			return false;
		}
		//Getting all crafts
		ArrayList<CraftArray> craftlist = plugin.craftStorage.getCrafts();
		//If craft number is lower than the higher craft number
		if(craftlist.size()-1 >= nb){
			CraftArray specraft = craftlist.get(nb);
			CraftFormatting.getCraftRecap(specraft, "§7" + Language.getTranslation("craftremove_removed"), false).send(player);
			//Removing
			plugin.craftStorage.removeCraft(nb);
			//Reload config ;)
			plugin.reloadConfig();
			//Display list
			player.performCommand("cc list");
			return true;
		}
		return true;
	}
	
}
