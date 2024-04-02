package com.taweerat.taweerattools.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateTaweeratTools {
    public static ItemStack createTool(Material m){
        ItemStack itemStack = new ItemStack(m, 1);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();

        if(m.toString().endsWith("PICKAXE")){
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "3x3 Pickaxe");
            lore.add(" ");
            lore.add("Hold shift to deactivate 3x3 function");
        }else if(m.toString().endsWith("SHOVEL")){
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "3x3 Shovel");
            lore.add(" ");
            lore.add("Hold shift to deactivate 3x3 function");
        }

        String input = itemStack.getType().name().toLowerCase().replace("_", " ");
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            result.append(capitalizedWord).append(" ");
        }

        assert meta != null;
        meta.setDisplayName("Taweerat " + result);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
