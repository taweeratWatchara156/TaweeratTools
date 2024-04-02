package com.taweerat.taweerattools.bar;

import com.taweerat.taweerattools.TaweeratTools;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

    private int taskId = -1;
    private final TaweeratTools plugin;
    private BossBar bar;

    public Bar(TaweeratTools plugin){
        this.plugin = plugin;
    }


    public void addPlayer(Player player){
        bar.addPlayer(player);
    }

    public BossBar getBar(){
        return this.bar;
    }

    public void createBar(String msg) {
        this.bar = Bukkit.createBossBar(msg, BarColor.RED, BarStyle.SOLID);
    }
}
