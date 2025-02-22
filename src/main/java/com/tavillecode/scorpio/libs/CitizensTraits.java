package com.tavillecode.scorpio.libs;

import com.tavillecode.scorpio.Scorpio;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.RotationTrait;
import net.citizensnpcs.trait.SneakTrait;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Interface39
 * @version 1.0
 * @description: TODO
 * @date 2025/2/8 16:30
 */
public class CitizensTraits {

    /**
     * 该函数旨在实现岚月宁被草CG
     */
    public static void beFucked(NPC npc) {
        npc.getOrAddTrait(LookClose.class).lookClose(false);
        npc.getOrAddTrait(RotationTrait.class).getPhysicalSession().rotateToHave(npc.getStoredLocation().getYaw()+180,npc.getStoredLocation().getPitch());
        final int[] time = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time[0] >= 30) {
                    npc.getOrAddTrait(SneakTrait.class).setSneaking(false);
                    this.cancel();
                }
                else {
                    npc.getOrAddTrait(SneakTrait.class).setSneaking(!npc.getOrAddTrait(SneakTrait.class).isSneaking());
                    time[0]++;
                }

            }
        }.runTaskTimer(Scorpio.getInstance(),20L,2L);
    }
}
