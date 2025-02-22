package com.tavillecode.scorpio.listeners;

import com.tavillecode.scorpio.objects.character.EmergingText;
import com.tavillecode.scorpio.objects.displayer.CDisplayer;
import net.citizensnpcs.api.CitizensAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerHeldItemListener implements Listener {
    @EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent e) {
        if (CDisplayer.PLAYER_STAGE.containsKey(e.getPlayer())) {
            if (CDisplayer.PLAYING_LIST.contains(e.getPlayer())) {
                e.getPlayer().getInventory().setHeldItemSlot(0);
                return;
            }
            final CDisplayer cDisplayer = CDisplayer.PLAYER_STAGE.get(e.getPlayer());
            if (!CitizensAPI.getNPCRegistry().getById(cDisplayer.getCharacter().getId()).getStoredLocation().getWorld().equals(e.getPlayer().getWorld())) {
                CDisplayer.PLAYER_STAGE.remove(e.getPlayer());
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
                return;
            }
            if (CitizensAPI.getNPCRegistry().getById(cDisplayer.getCharacter().getId()).getStoredLocation().distance(e.getPlayer().getLocation()) > 3) {
                CDisplayer.PLAYER_STAGE.remove(e.getPlayer());
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
                return;
            }
            final EmergingText em = cDisplayer.getCharacter().getEmergingTexts().get(cDisplayer.getStage() - 1);
            if (e.getNewSlot() + 1 > em.getSelections().size()) {
                e.setCancelled(true);
            } else {
                if (!CDisplayer.PLAYING_LIST.contains(e.getPlayer())) {
                    cDisplayer.sendOptionsText(e.getPlayer(),e.getNewSlot());
                    MiniMessage mm = MiniMessage.miniMessage();
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER,1.0F,0.5F);
                    e.getPlayer().sendActionBar(mm.deserialize("<!italic><white>滚动 <yellow>鼠标滚轮 <white>选择对话并按下 <yellow>F <white>回复."));
                }
            }
        }
    }
}
