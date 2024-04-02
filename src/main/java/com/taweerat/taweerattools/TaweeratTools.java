package com.taweerat.taweerattools;

import com.taweerat.taweerattools.listeners.TaweeratToolsListeners;
import com.taweerat.taweerattools.listeners.Timber;
import com.taweerat.taweerattools.listeners.Veinminer;
import com.taweerat.taweerattools.recipes.CreateTaweeratTools;
import com.taweerat.taweerattools.recipes.recipes;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class TaweeratTools extends JavaPlugin {
    private static TaweeratTools instance;
    public List<String> configTimberBlock;
    public List<String> configPickaxeBlock;
    public List<String> configShovelBlock;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        configTimberBlock = getConfig().getStringList("validTimberBlocks");
        configPickaxeBlock = getConfig().getStringList("validPickaxeBlocks");
        configShovelBlock = getConfig().getStringList("validShovelBlocks");

        getServer().getPluginManager().registerEvents(new TaweeratToolsListeners(), this);
        getServer().getPluginManager().registerEvents(new Veinminer(), this);
        getServer().getPluginManager().registerEvents(new Timber(), this);

        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.STONE_PICKAXE), Material.STONE_PICKAXE, Material.STONE, "taweerat_stone_pickaxe", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.IRON_PICKAXE), Material.IRON_PICKAXE, Material.IRON_BLOCK, "taweerat_iron_pickaxe", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.GOLDEN_PICKAXE), Material.GOLDEN_PICKAXE, Material.GOLD_BLOCK, "taweerat_golden_pickaxe", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.DIAMOND_PICKAXE), Material.DIAMOND_PICKAXE, Material.DIAMOND_BLOCK, "taweerat_diamond_pickaxe", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.NETHERITE_PICKAXE), Material.NETHERITE_PICKAXE, Material.NETHERITE_BLOCK, "taweerat_netherite_pickaxe", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.STONE_SHOVEL), Material.STONE_SHOVEL, Material.STONE, "taweerat_stone_shovel", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.IRON_SHOVEL), Material.IRON_SHOVEL, Material.IRON_BLOCK, "taweerat_iron_shovel", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.GOLDEN_SHOVEL), Material.GOLDEN_SHOVEL, Material.GOLD_BLOCK, "taweerat_golden_shovel", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.DIAMOND_SHOVEL), Material.DIAMOND_SHOVEL, Material.DIAMOND_BLOCK, "taweerat_diamond_shovel", this);
        recipes.shapeLessRecipes(CreateTaweeratTools.createTool(Material.NETHERITE_SHOVEL), Material.NETHERITE_SHOVEL, Material.NETHERITE_BLOCK, "taweerat_netherite_shovel", this);
    }

    public static TaweeratTools getInstance(){
        return instance;
    }
}
