package com.taweerat.taweeratTools.listerners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static String getPickaxeName(Material material){
        String result = material.name().toLowerCase();
        result = result.replace("_", " ");
        result = capitalizeFirstLetters(result);

        return result;
    }

    public static String capitalizeFirstLetters(String input) {
        // Split the input string into words
        String[] words = input.split(" ");

        // Iterate through each word
        for (int i = 0; i < words.length; i++) {
            // Capitalize the first letter of each word
            if (words[i].length() > 0) {
                words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            }
        }

        // Join the words back into a single string
        return String.join(" ", words);
    }


    public static ItemStack getTaweeratPickaxe(Material material){
        ItemStack pickaxe = new ItemStack(material);
        ItemMeta meta = pickaxe.getItemMeta();
        if(meta != null){

            meta.setDisplayName("Taweerat " + getPickaxeName(material));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.BOLD + meta.getDisplayName());
            lore.add(" ");
            lore.add("3x3");
            meta.setLore(lore);
            pickaxe.setItemMeta(meta);
        }

        return pickaxe;
    }

    public static ItemStack getTaweeratShovel(Material material){
        ItemStack pickaxe = new ItemStack(material);
        ItemMeta meta = pickaxe.getItemMeta();
        if(meta != null){

            meta.setDisplayName("Taweerat " + getPickaxeName(material));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.BOLD + meta.getDisplayName());
            lore.add(" ");
            lore.add("3x3");
            meta.setLore(lore);
            pickaxe.setItemMeta(meta);
        }

        return pickaxe;
    }
}
