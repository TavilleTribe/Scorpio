package com.tavillecode.scorpio.listeners.citizens;

import com.tavillecode.scorpio.objects.character.Character;
import com.tavillecode.scorpio.objects.displayer.CDisplayer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class RightClickNPCListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onPlayerRightClickNPC(NPCRightClickEvent e) {
        if (Character.CHARACTERS.containsKey(e.getNPC().getId())&&!CDisplayer.PLAYER_STAGE.containsKey(e.getClicker())) {
            if (e.getClicker().getInventory().getHeldItemSlot() != 0) {
                e.getClicker().getInventory().setHeldItemSlot(0);
            }
            final Character c = Character.CHARACTERS.get(e.getNPC().getId());
            e.getClicker().playSound(e.getClicker().getLocation(), Sound.ENTITY_ITEM_PICKUP,1F,0.5F);
            CDisplayer cDisplayer = new CDisplayer(e.getClicker(),c);
            CDisplayer.PLAYING_LIST.add(e.getClicker());
            CDisplayer.PLAYER_STAGE.put(e.getClicker(),cDisplayer);
            cDisplayer.playText();
            MiniMessage mm = MiniMessage.miniMessage();
            e.getClicker().sendActionBar(mm.deserialize("<!italic><white>滚动 <yellow>鼠标滚轮 <white>选择对话并按下 <yellow>F <white>回复."));
            e.getClicker().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,99999,3));
            e.getClicker().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,99999,1));
        }
    }
//    private void changeSkin(Block b, String base64Str) {
//        if (b.getType() != Material.PLAYER_HEAD) return;    // to avoid spurious exceptions
//        final Skull skull = (Skull)b.getState();
//        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
//        profile.getProperties().put("textures", new Property("textures",base64Str));
//        try {
//            Field profileField = skull.getClass().getDeclaredField("profile");
//            profileField.setAccessible(true);
//            profileField.set(skull, profile);
//        }catch (NoSuchFieldException | IllegalAccessException e) { e.printStackTrace(); }
//        skull.update(); // so that the result can be seen
//    }
}
