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

public class Timber implements Listener {
    TaweeratTools instance = TaweeratTools.getInstance();
    Map<String, Boolean> timberState = new HashMap<>();
    Bar stateBar = new Bar(TaweeratTools.getInstance());
    int shiftTimer = 0;
    BukkitTask runnable;

    Bar bar = new Bar(TaweeratTools.getInstance().getInstance());

    @EventHandler
    public void tb(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int delay = 2;

        if(timberState.get(player.getUniqueId().toString()) != null && timberState.get(player.getUniqueId().toString()) &&
                instance.configTimberBlock.contains(block.getType().toString())){

            List<Block> timberBlocks = new ArrayList<>();
            findTimberBlocks(block, timberBlocks);

            if(timberBlocks.size() > 1){
                for (int i = 0;i < timberBlocks.size();i++){
                    Block b = timberBlocks.get(i);if(b != null || !b.getType().equals(Material.AIR)){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 0.5f, 1f);
                                b.breakNaturally(player.getInventory().getItemInMainHand());
                                timberBlocks.remove(b);
                            }
                        }.runTaskLater(instance, delay);

                        delay += 2;
                    }
                }
            }
        }
    }

    public void findTimberBlocks(Block block, List<Block> result) {
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
                        findTimberBlocks(blockFound, result);
                    }

                    if(blockFound2.getType().equals(block.getType())){
                        findTimberBlocks(blockFound2, result);
                    }

                    if(blockFound3.getType().equals(block.getType())){
                        findTimberBlocks(blockFound3, result);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerToggleTimber(PlayerToggleSneakEvent event){
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
            if(timberState.get(player.getUniqueId().toString()) == null || timberState.get(player.getUniqueId().toString()).equals(false)){
                bar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ Hold shift for 3 seconds to turn" + ChatColor.GREEN + "" + ChatColor.BOLD + " ON " + ChatColor.GOLD + ChatColor.BOLD +"Timber ]");
            }else{
                bar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ Hold shift for 3 seconds to turn" + ChatColor.RED + "" + ChatColor.BOLD + " OFF " + ChatColor.GOLD + ChatColor.BOLD +"Timber ]");
            }
            shiftTimer = 0;
        }

        if(!player.isSneaking() && itemInMainHand.getType().toString().endsWith("_AXE")) {

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
                            if(timberState.get(player.getUniqueId().toString()) == null || !timberState.get(player.getUniqueId().toString())){
                                timberState.put(player.getUniqueId().toString(), true);
                                stateBar.createBar(ChatColor.GOLD + "" + ChatColor.BOLD + "[ Timber : "
                                        + ChatColor.GREEN + "" + ChatColor.BOLD + "ON " + ChatColor.GOLD + "" + ChatColor.BOLD + "]");
                                stateBar.addPlayer(player);
                            }else{
                                timberState.put(player.getUniqueId().toString(), false);
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
        timberState.put(player.getUniqueId().toString(), false);
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
