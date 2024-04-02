package com.taweerat.taweerattools.listeners;

import com.taweerat.taweerattools.TaweeratTools;
import com.taweerat.taweerattools.bar.Bar;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Veinminer implements Listener {
    TaweeratTools instance = TaweeratTools.getInstance();
    Map<String, Boolean> veinMinerState = new HashMap<>();
    Bar stateBar = new Bar(TaweeratTools.getInstance());
    int shiftTimer = 0;
    BukkitTask runnable;

    Bar bar = new Bar(TaweeratTools.getInstance().getInstance());

    @EventHandler
    public void vm(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(veinMinerState.get(player.getUniqueId().toString()) != null && veinMinerState.get(player.getUniqueId().toString()) &&
            isOre(block.getType()) && player.getInventory().getItemInMainHand().getType().toString().endsWith("PICKAXE")){

            List<Block> veinBlocks = new ArrayList<>();
            findVeinBlock(block, veinBlocks);

            if(veinBlocks.size() > 1){
                int xp =  0;

                for (int i = 0;i < veinBlocks.size();i++){
                    Block b = veinBlocks.get(i);if(b != null || !b.getType().equals(Material.AIR)){
                        xp += veinDropExp(b);
                        if(i == veinBlocks.size() - 1){
                            if(xp != 0){
                                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(xp);
                            }
                        }
                        b.breakNaturally(player.getInventory().getItemInMainHand());
                    }
                }
            }
        }
    }

    public void findVeinBlock(Block block, List<Block> result) {
        if (result.contains(block)) {
            return;
        }

        result.add(block);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block blockFound = block.getWorld().getBlockAt(block.getLocation().add(dx, 0, 0));
                    Block blockFound2 = block.getWorld().getBlockAt(block.getLocation().add(0, dy, 0));
                    Block blockFound3 = block.getWorld().getBlockAt(block.getLocation().add(0, 0, dz));
                    if(blockFound.getType().equals(block.getType())){
                        findVeinBlock(blockFound, result);
                    }

                    if(blockFound2.getType().equals(block.getType())){
                        findVeinBlock(blockFound2, result);
                    }

                    if(blockFound3.getType().equals(block.getType())){
                        findVeinBlock(blockFound3, result);
                    }
                }
            }
        }
    }

    public int veinDropExp(Block block){
        Material type = block.getType();
        if(type.equals(Material.COAL_ORE) || type.equals(Material.DEEPSLATE_COAL_ORE)){
            // xp 0-2
            int xpToDrop = new Random().nextInt(3);
            if(xpToDrop != 0){
                return xpToDrop;
            }
        }else if(type.equals(Material.NETHER_GOLD_ORE)){
            // xp 0-1
            int xpToDrop = new Random().nextInt(2);
            if(xpToDrop != 0){
                return xpToDrop;
            }
        }else if(type.equals(Material.DIAMOND_ORE) || type.equals(Material.DEEPSLATE_DIAMOND_ORE)
                || type.equals(Material.EMERALD_ORE) || type.equals(Material.DEEPSLATE_EMERALD_ORE)){
            // xp 3-7
            int xpToDrop = new Random().nextInt(5) + 3;
            return xpToDrop;
        }else if (type.equals(Material.LAPIS_ORE) || type.equals(Material.DEEPSLATE_LAPIS_ORE) ||
                type.equals(Material.NETHER_QUARTZ_ORE)){
            // xp 2-5
            int xpToDrop = new Random().nextInt(4) + 2;
            return xpToDrop;
        }else if(type.equals(Material.REDSTONE_ORE) || type.equals(Material.DEEPSLATE_REDSTONE_ORE)){
            // xp 1-5
            int xpToDrop = new Random().nextInt(5) + 1;
            return xpToDrop;
        }

        return -1;
    }

    private boolean isOre(Material material) {
        return material.equals(Material.COAL_ORE) ||
                material.equals(Material.IRON_ORE) ||
                material.equals(Material.GOLD_ORE) ||
                material.equals(Material.DIAMOND_ORE) ||
                material.equals(Material.EMERALD_ORE) ||
                material.equals(Material.REDSTONE_ORE) ||
                material.equals(Material.LAPIS_ORE) ||
                material.equals(Material.DEEPSLATE_COAL_ORE) ||
                material.equals(Material.DEEPSLATE_IRON_ORE) ||
                material.equals(Material.DEEPSLATE_GOLD_ORE) ||
                material.equals(Material.DEEPSLATE_DIAMOND_ORE) ||
                material.equals(Material.DEEPSLATE_EMERALD_ORE) ||
                material.equals(Material.DEEPSLATE_REDSTONE_ORE) ||
                material.equals(Material.DEEPSLATE_LAPIS_ORE) ||
                material.equals(Material.NETHER_QUARTZ_ORE) ||
                material.equals(Material.NETHER_GOLD_ORE) ||
                material.equals(Material.COPPER_ORE) ||
                material.equals(Material.DEEPSLATE_COPPER_ORE);
        // Add more ore types as needed
    }

    @EventHandler
    public void playerToggleVeinMiner(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();



        if(player.isSneaking()){
            if(bar.getBar() != null){
                bar.getBar().removePlayer(player);
            }
            if(runnable != null){
                runnable.cancel();
            }
        }else{
            if(veinMinerState.get(player.getUniqueId().toString()) == null || veinMinerState.get(player.getUniqueId().toString()).equals(false)){
                bar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ Hold shift for 3 seconds to turn" + ChatColor.GREEN + "" + ChatColor.BOLD + " ON " + ChatColor.GOLD + ChatColor.BOLD +"Veinminer ]");
            }else{
                bar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ Hold shift for 3 seconds to turn" + ChatColor.RED + "" + ChatColor.BOLD + " OFF " + ChatColor.GOLD + ChatColor.BOLD +"Veinminer ]");
            }
            shiftTimer = 0;
        }

        if(!player.isSneaking() && itemInMainHand.getType().toString().endsWith("PICKAXE")) {

            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if(player.isSneaking()){
                        shiftTimer += 1;
                        bar.addPlayer(player);
                        if(shiftTimer == 1){
                            bar.getBar().setProgress(0.34);
                            player.playSound(player, Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 1F);
                        }else if(shiftTimer == 2){
                            bar.getBar().setProgress(0.68);
                            player.playSound(player, Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 1F);
                        }

                        if(shiftTimer == 3){
                            bar.getBar().setProgress(1);
                            bar.getBar().removePlayer(player);
                            player.playSound(player, Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 2F);
                            if(veinMinerState.get(player.getUniqueId().toString()) == null || !veinMinerState.get(player.getUniqueId().toString())){
                                veinMinerState.put(player.getUniqueId().toString(), true);
                                stateBar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ VeinMiner : "
                                        + ChatColor.GREEN + "" + ChatColor.BOLD + "ON " + ChatColor.GOLD + "" + ChatColor.BOLD + "]");
                                stateBar.addPlayer(player);
                            }else{
                                veinMinerState.put(player.getUniqueId().toString(), false);
                                stateBar.getBar().removePlayer(player);
                            }
                            this.cancel();
                        }
                    }else{
                        this.cancel();
                    }
                }
            }.runTaskTimer(TaweeratTools.getInstance(), 20L, 20L);
        }
    }

    @EventHandler
    public void onServerReload(ServerCommandEvent event){
        String command = event.getCommand();
        if(command.equalsIgnoreCase("reload confirm")){

            if(stateBar.getBar() != null){
                for (Player player : stateBar.getBar().getPlayers()){
                    stateBar.getBar().removePlayer(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        veinMinerState.put(player.getUniqueId().toString(), false);
    }

    @EventHandler
    public void onPlayerSendReload(PlayerCommandPreprocessEvent event){
        String command = event.getMessage().toLowerCase();
        if (command.equals("/reload confirm")) {
            if(stateBar.getBar() != null){
                for (Player player : stateBar.getBar().getPlayers()){
                    stateBar.getBar().removePlayer(player);
                }
            }
        }
    }
}
