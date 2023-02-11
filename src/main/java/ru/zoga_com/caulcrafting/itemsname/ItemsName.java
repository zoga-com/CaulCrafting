package ru.zoga_com.caulcrafting.itemsname;

import org.bukkit.inventory.ItemStack;

public class ItemsName implements Itemsname {
    @Override
    public String getItemStackName(ItemStack stack){
        String name;
        int amt = stack.getAmount();

        try {
            if(stack.getItemMeta().getDisplayName().length() >= 1) {
                name =  stack.getItemMeta().getDisplayName();
            } else {
                name = stack.getType().name();
            }
        } catch (Exception e) {
            name = stack.getType().name();
        }

        if(amt > 1) name += " §3x " + amt;
        return name;
    }
}
