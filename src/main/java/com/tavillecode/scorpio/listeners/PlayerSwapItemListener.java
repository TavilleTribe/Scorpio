package com.tavillecode.scorpio.listeners;

import com.tavillecode.scorpio.libs.CitizensTraits;
import com.tavillecode.scorpio.objects.displayer.CDisplayer;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerSwapItemListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerSwapItemEvent(PlayerSwapHandItemsEvent e) {
        if (CDisplayer.PLAYER_STAGE.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            if (CDisplayer.PLAYING_LIST.contains(e.getPlayer())) {
                return;
            }
            CDisplayer cDisplayer = CDisplayer.PLAYER_STAGE.get(e.getPlayer());
            if (CitizensAPI.getNPCRegistry().getById(cDisplayer.getCharacter().getId()).getStoredLocation().distance(e.getPlayer().getLocation()) > 9){
                CDisplayer.PLAYER_STAGE.remove(e.getPlayer());
                return;
            }
            final int i = cDisplayer.getCharacter().getEmergingTexts().get(cDisplayer.getStage() - 1).getSelections().get(e.getPlayer().getInventory().getHeldItemSlot()).executeSelection(e.getPlayer());
            if (i == -1) {
                cDisplayer.setStage(cDisplayer.getStage() + 1);
                cDisplayer.playText();
                CDisplayer.PLAYER_STAGE.put(e.getPlayer(),cDisplayer);
            }
            else if (i == -2) {
                //CitizensTraits.beFucked(CitizensAPI.getNPCRegistry().getById(cDisplayer.getCharacter().getId()));
                CDisplayer.PLAYING_LIST.remove(e.getPlayer());
                CDisplayer.PLAYER_STAGE.remove(e.getPlayer());
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
            }
            else if (i > 0){
                cDisplayer.setStage(i);
                CDisplayer.PLAYING_LIST.add(e.getPlayer());
                cDisplayer.playText();
                CDisplayer.PLAYER_STAGE.put(e.getPlayer(),cDisplayer);
            }
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP,1F,0.5F);
        }
    }
}
