package com.tavillecode.scorpio.objects.character;

import java.util.List;

public class EmergingText {
    private final List<String> content;
    private final List<Selection> selections;

    public EmergingText(List<String> content, List<Selection> selections) {
        this.content = content;
        this.selections = selections;
    }

    public List<String> getContent() {
        return this.content;
    }

    public List<Selection> getSelections() {
        return selections;
    }
}
