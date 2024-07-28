package com.taweerat.taweeratTools;

import com.taweerat.taweeratTools.listerners.TaweeratToolListener;
import com.taweerat.taweeratTools.listerners.Tools;
import com.taweerat.taweeratTools.model.PickaxeMaterials;
import com.taweerat.taweeratTools.model.ShovelMaterials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class TaweeratTools extends JavaPlugin {
    private final Logger logger = getLogger();
    private final List<NamespacedKey> tags = new ArrayList<>();
    List<Material> pickaxeMats = new ArrayList<>();
    List<Material> shovelMats = new ArrayList<>();


    @Override
    public void onEnable() {
        addPickaxe(Material.DIAMOND_PICKAXE, Material.DIAMOND_BLOCK);
        addPickaxe(Material.NETHERITE_PICKAXE, Material.NETHERITE_BLOCK);
        addPickaxe(Material.GOLDEN_PICKAXE, Material.GOLD_BLOCK);
        addPickaxe(Material.IRON_PICKAXE, Material.IRON_BLOCK);

        addShovel(Material.DIAMOND_SHOVEL, Material.DIAMOND_BLOCK);
        addShovel(Material.NETHERITE_SHOVEL, Material.NETHERITE_BLOCK);
        addShovel(Material.GOLDEN_SHOVEL, Material.GOLD_BLOCK);
        addShovel(Material.IRON_SHOVEL, Material.IRON_BLOCK);
        

        saveDefaultConfig();
        initConfig();
        pickaxeMats = loadConfig();
        shovelMats = loadShovelConfig();

//        Event
        getServer().getPluginManager().registerEvents(new TaweeratToolListener(this), this);
    }

    public void addPickaxe(Material mat ,Material mat2){
        ItemStack itemStack = Tools.getTaweeratPickaxe(mat);
        NamespacedKey key = new NamespacedKey(this, "pickaxe." + itemStack.getType().name());
        ShapelessRecipe recipe = new ShapelessRecipe(key, itemStack);
        recipe.addIngredient(mat);
        recipe.addIngredient(mat2);
        Bukkit.addRecipe(recipe);

        tags.add(key);

    }

    public void addShovel(Material mat ,Material mat2){
        ItemStack itemStack = Tools.getTaweeratPickaxe(mat);
        NamespacedKey key = new NamespacedKey(this, "shovel." + itemStack.getType().name());
        ShapelessRecipe recipe = new ShapelessRecipe(key, itemStack);
        recipe.addIngredient(mat);
        recipe.addIngredient(mat2);
        Bukkit.addRecipe(recipe);

        tags.add(key);

    }

    private void initConfig(){
        List<String> map = new ArrayList<>();

        if(!getConfig().contains("properMatsPickaxe")){
            for (PickaxeMaterials bm : PickaxeMaterials.values()){
                Material mat = Material.valueOf(bm.toString());
                map.add(mat.toString());
            }
            getConfig().set("properMatsPickaxe", map);
        }


        List<String> map2 = new ArrayList<>();

        if(!getConfig().contains("properMatsShovel")){
            for (ShovelMaterials bm : ShovelMaterials.values()){
                Material mat = Material.valueOf(bm.toString());
                map2.add(mat.toString());
            }
            getConfig().set("properMatsShovel", map2);
        }


        saveConfig();
    }

    private List<Material> loadConfig(){
        List<Material> mat = new ArrayList<>();

        if(getConfig().contains("properMatsPickaxe")){
            List<String> res = getConfig().getStringList("properMatsPickaxe");
            for (String m : res){
                Material material = Material.valueOf(m);
                mat.add(material);
            }
        }

        return mat;
    }

    private List<Material> loadShovelConfig(){
        List<Material> mat = new ArrayList<>();

        if(getConfig().contains("properMatsShovel")){
            List<String> res = getConfig().getStringList("properMatsShovel");
            for (String m : res){
                Material material = Material.valueOf(m);
                mat.add(material);
            }
        }

        return mat;
    }


    @Override
    public void onDisable() {

    }

    public List<NamespacedKey> getTags() {
        return tags;
    }

    public List<Material> getPickaxeMaterials() {
        return pickaxeMats;
    }

    public List<Material> getShovelMaterials() {
        return shovelMats;
    }
}
