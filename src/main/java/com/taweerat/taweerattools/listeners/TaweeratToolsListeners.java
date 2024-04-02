package com.taweerat.taweerattools.listeners;

import com.taweerat.taweerattools.TaweeratTools;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class TaweeratToolsListeners implements Listener {
    TaweeratTools instance = TaweeratTools.getInstance();
    HashMap<String, BlockFace> facing = new HashMap<>();

    @EventHandler
    public void getFacing(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();

        if(action.equals(Action.LEFT_CLICK_BLOCK)){
            facing.put(player.getName(), event.getBlockFace());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        if(itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && !player.isSneaking()){
            ItemMeta meta = itemInHand.getItemMeta();
            String itemName = meta.getDisplayName();

            if(itemName.startsWith("Taweerat")){
                if(itemName.endsWith("Pickaxe ")){
                    if(instance.configPickaxeBlock.contains(block.getType().toString())){
                        for(Block b : getBlockBreak(facing.get(player.getName()), block, player)){
                            if(instance.configPickaxeBlock.contains(b.getType().toString())){
                                b.breakNaturally(itemInHand);
                            }
                        }
                    }
                }else if(itemName.endsWith("Shovel ")){
                    if(instance.configShovelBlock.contains(block.getType().toString())){
                        for(Block b : getBlockBreak(facing.get(player.getName()), block, player)){
                            if(instance.configShovelBlock.contains(b.getType().toString())){
                                b.breakNaturally(itemInHand);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Block> getBlockBreak(BlockFace face, Block block, Player player){
        ArrayList<Block> blockList = new ArrayList<>();

        if(face.equals(BlockFace.SOUTH) || face.equals(BlockFace.NORTH)){
            for (int x = -1;x <= 1;x++){
                for (int y = -1;y <= 1;y++){
                    blockList.add(player.getWorld().getBlockAt(block.getLocation().add(x, y, 0)));
                }
            }
        }else if(face.equals(BlockFace.WEST) || face.equals(BlockFace.EAST)){
            for (int y = -1;y <= 1;y++){
                for (int z = -1;z <= 1;z++){
                    blockList.add(player.getWorld().getBlockAt(block.getLocation().add(0, y, z)));
                }
            }
        }else if(face.equals(BlockFace.UP) || face.equals(BlockFace.DOWN)){
            for (int x = -1;x <= 1;x++){
                for (int z = -1;z <= 1;z++){
                    blockList.add(player.getWorld().getBlockAt(block.getLocation().add(x, 0, z)));
                }
            }
        }

        return blockList;
    }
}
