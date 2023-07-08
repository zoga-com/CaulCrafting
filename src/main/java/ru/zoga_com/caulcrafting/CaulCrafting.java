package ru.zoga_com.caulcrafting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.zoga_com.caulcrafting.commands.CaulCraftingCommandExecutor;
import ru.zoga_com.caulcrafting.commands.CaulCraftingConfigCommandExecutor;
import ru.zoga_com.caulcrafting.itemsname.*;
import ru.zoga_com.caulcrafting.listeners.AsyncPlayerChatListener;
import ru.zoga_com.caulcrafting.listeners.BlockPistonExtendListener;
import ru.zoga_com.caulcrafting.listeners.BlockPistonRetractListener;
import ru.zoga_com.caulcrafting.listeners.ItemDropListener;
import ru.zoga_com.caulcrafting.editor.Editor;

public class CaulCrafting extends JavaPlugin implements Listener {
	//Editors working
	public static Map<UUID,Editor> editors = new HashMap<>();
	//Data folder
	public static File dataFolder = null;
	public ConfigUpdate configUpdate = new ConfigUpdate(this);
	public Config configUtils = new Config(this);
	public CraftStorage craftStorage = new CraftStorage(this);
	//Languages availables list 
	public HashMap<String, String> languagesAvailable = new HashMap<String,String>();

	@Override
	public void onEnable(){
		ConfigurationSerialization.registerClass(CraftArray.class);
		//Save data folder
		CaulCrafting.dataFolder = this.getDataFolder();
		//Commands executors
		this.getCommand("caulcrafting").setExecutor(new CaulCraftingCommandExecutor(this));
		this.getCommand("caulcraftingconfig").setExecutor(new CaulCraftingConfigCommandExecutor(this));
		//LISTENERS - EVENTS register
		PluginManager plugman = getServer().getPluginManager();
		plugman.registerEvents(new AsyncPlayerChatListener(this), this);
		plugman.registerEvents(new ItemDropListener(this), this);
		plugman.registerEvents(new BlockPistonExtendListener(this), this);
		plugman.registerEvents(new BlockPistonRetractListener(this), this);
		//Setup languages
		languagesAvailable.put("en", "English");
		languagesAvailable.put("fr", "Français");
		languagesAvailable.put("ru", "Русский");
		languagesAvailable.put("nl", "Nederlands");
		languagesAvailable.put("de", "Deutsch");
		languagesAvailable.put("ja", "日本語");
		languagesAvailable.put("pl", "Polski");
		languagesAvailable.put("vi", "Tiếng Việt");
		languagesAvailable.put("es", "Español");
		languagesAvailable.put("pt", "Português");
		languagesAvailable.put("zh", "中文");
		languagesAvailable.put("hu", "Magyar");
		languagesAvailable.put("lv", "Latviešu valoda");
		languagesAvailable.put("ko", "한국어");
		languagesAvailable.put("cs", "Česky");
		//Defaults configs files (locales..)
		configUtils.setupDefaults();
		//Load defaults configs if empty
		saveDefaultConfig();
		//Updating config
		configUpdate.update();
		//nms class for items name utils
		if(setupItemsname()){
			nmsItemsName = true;
		} else {
			getLogger().severe(Language.getTranslation("updater_warn_1"));
			getLogger().severe(Language.getTranslation("updater_warn_2"));
			nmsItemsName = false;
		}
		//Stats (bstats) https://bstats.org/plugin/bukkit/CaulCrafting
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("used_languages", new Callable<String>() {
	        @Override
	        public String call() throws Exception {
	            return Language.getLanguage();
	        }
	    }));
	} 
	
	static Itemsname itemsname = null;
	public static boolean nmsItemsName = false;
	
	public static Itemsname getItemsname(){
		return itemsname;
	}
	
	private boolean setupItemsname(){
		itemsname = new ItemsName();
		return true;
	}
	
	public ArrayList<UUID> craftProc = new ArrayList<UUID>();
	public HashMap<UUID,Location> caulLoc = new HashMap<UUID,Location>();
	public HashMap<UUID,ArrayList<ItemStack>> inCaulFin = new HashMap<UUID,ArrayList<ItemStack>>();
	
	public void sendDebug(Player player, String msg) {
		if(getConfig().getBoolean("debug_message")) {
			getLogger().info("CaulCrafting DEBUG " + player.getName() + ": " + msg);
		}
	}

}
