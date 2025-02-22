package com.tavillecode.scorpio.objects.plot;

import com.tavillecode.scorpio.objects.character.EmergingText;
import com.tavillecode.scorpio.objects.character.Selection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Interface39
 * @version 1.0
 * @description: TODO
 * @date 2024/9/28 10:38
 */
public class Plots extends EmergingText {
    private final int Stage;

    public Plots(List<String> content, ArrayList<Selection> selections, int stage) {
        super(content, selections);
        Stage = stage;
    }

    public int getStage() {
        return this.Stage;
    }
}
