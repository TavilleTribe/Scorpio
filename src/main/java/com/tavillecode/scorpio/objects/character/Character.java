package com.tavillecode.scorpio.objects.character;

import com.tavillecode.scorpio.objects.plot.Plots;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Character {
    private final int id;
    private final String npcName;
    private final ArrayList<EmergingText> emergingTexts;
    private final ArrayList<Plots> plots;

    public Character(int id, String npcName, ArrayList<EmergingText> emergingTexts, ArrayList<Plots> plots) {
        this.id = id;
        this.npcName = npcName;
        this.emergingTexts = emergingTexts;
        this.plots = plots;
    }

    public int getId() {
        return id;
    }

    public String getNPCName() {
        return this.npcName;
    }

    public ArrayList<EmergingText> getEmergingTexts() {
        return emergingTexts;
    }

    public ArrayList<Plots> getPlots() {
        return plots;
    }

    public static ConcurrentHashMap<Integer,Character> CHARACTERS;

    static {
        CHARACTERS = new ConcurrentHashMap<>();
    }
}
