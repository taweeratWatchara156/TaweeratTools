package com.taweerat.taweerattools.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class recipes {

    public static void shapeLessRecipes(ItemStack i, Material m, Material m1,String name, JavaPlugin plugin){
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, name), i);
        recipe.addIngredient(m);
        recipe.addIngredient(m1);

        Bukkit.addRecipe(recipe);
    }
}
