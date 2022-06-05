package com.github.firewolf8385.woolwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.getEntity().setFoodLevel(20);
    }
}
