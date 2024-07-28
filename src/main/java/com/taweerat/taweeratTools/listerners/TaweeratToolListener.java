package com.taweerat.taweeratTools.listerners;

import com.taweerat.taweeratTools.TaweeratTools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class TaweeratToolListener implements Listener {
    public Map<String, BlockFace> map = new HashMap<>();
    final TaweeratTools instance;

    public TaweeratToolListener(TaweeratTools plugin) {
        instance = plugin;
    }

    @EventHandler
    public void blockFace(PlayerInteractEvent event){
        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            map.put(event.getPlayer().getUniqueId().toString(), event.getBlockFace());
        }
    }

    @EventHandler
    public void playerBreakBlock(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.hasItemMeta()){
            ItemMeta meta = itemStack.getItemMeta();
            if(meta.getDisplayName().startsWith("Taweerat") && meta.getDisplayName().endsWith("Pickaxe") && !player.isSneaking()){
                if(map.get(player.getUniqueId().toString()).equals(BlockFace.UP) || map.get(player.getUniqueId().toString()).equals(BlockFace.DOWN)){
                    for (int x = -1;x <= 1;x++){
                        for (int z = -1;z <= 1;z++){
                            Block target = player.getWorld().getBlockAt(block.getX() + x, block.getY(), block.getZ() + z);
                            breakBlockWithPickaxe(target);
                        }
                    }
                }else if(map.get(player.getUniqueId().toString()).equals(BlockFace.NORTH) || map.get(player.getUniqueId().toString()).equals(BlockFace.SOUTH)){
                    for (int x = -1;x <= 1;x++){
                        for (int y = -1;y <= 1;y++){
                            Block target = player.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ());
                            breakBlockWithPickaxe(target);
                        }
                    }
                }else if(map.get(player.getUniqueId().toString()).equals(BlockFace.EAST) || map.get(player.getUniqueId().toString()).equals(BlockFace.WEST)){
                    for (int z = -1;z <= 1;z++){
                        for (int y = -1;y <= 1;y++){
                            Block target = player.getWorld().getBlockAt(block.getX(), block.getY() + y, block.getZ() + z);
                            breakBlockWithPickaxe(target);
                        }
                    }
                }
            }else if(meta.getDisplayName().startsWith("Taweerat") && meta.getDisplayName().endsWith("Shovel") && !player.isSneaking()){
                if(map.get(player.getUniqueId().toString()).equals(BlockFace.UP) || map.get(player.getUniqueId().toString()).equals(BlockFace.DOWN)){
                    for (int x = -1;x <= 1;x++){
                        for (int z = -1;z <= 1;z++){
                            Block target = player.getWorld().getBlockAt(block.getX() + x, block.getY(), block.getZ() + z);
                            breakBlockWithShovel(target);
                        }
                    }
                }else if(map.get(player.getUniqueId().toString()).equals(BlockFace.NORTH) || map.get(player.getUniqueId().toString()).equals(BlockFace.SOUTH)){
                    for (int x = -1;x <= 1;x++){
                        for (int y = -1;y <= 1;y++){
                            Block target = player.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ());
                            breakBlockWithShovel(target);
                        }
                    }
                }else if(map.get(player.getUniqueId().toString()).equals(BlockFace.EAST) || map.get(player.getUniqueId().toString()).equals(BlockFace.WEST)){
                    for (int z = -1;z <= 1;z++){
                        for (int y = -1;y <= 1;y++){
                            Block target = player.getWorld().getBlockAt(block.getX(), block.getY() + y, block.getZ() + z);
                            breakBlockWithShovel(target);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerEnterServer(PlayerJoinEvent event){
        Player player = event.getPlayer();
        for (NamespacedKey key : instance.getTags()){
            player.discoverRecipe(key);
        }
    }

    public void breakBlockWithPickaxe(Block block){
        if(instance.getPickaxeMaterials().contains(block.getType())){
            block.breakNaturally();
        }
    }

    public void breakBlockWithShovel(Block block){
        if(instance.getShovelMaterials().contains(block.getType())){
            block.breakNaturally();
        }
    }
}
