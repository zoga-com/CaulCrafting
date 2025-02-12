package ru.zoga_com.caulcrafting.editor;

import org.bukkit.entity.Player;

import ru.zoga_com.caulcrafting.CaulCrafting;
import ru.zoga_com.caulcrafting.CraftArray;

public class PlayerEditor {

	public static boolean isInEditor(Player player) {
		if (CaulCrafting.editors.containsKey(player.getUniqueId()))
			return true;
		return false;
	}
	
	public static Editor getEditor(Player player) {
		if (isInEditor(player)) {
			return (CaulCrafting.editors.get(player.getUniqueId()));
		}
		return null;
	}
	
	public static CraftArray getEditorCraft(Player player) {
		Editor editor = getEditor(player);
		
		if (editor != null)
			return (editor.getCraft());
		return null;
	}
	
	public static void exitEditor(Player player) {
		if (isInEditor(player))
			CaulCrafting.editors.remove(player.getUniqueId());
	}
}
