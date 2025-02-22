package com.tavillecode.scorpio.objects.displayer;

import com.tavillecode.scorpio.Scorpio;
import com.tavillecode.scorpio.objects.character.Character;
import com.tavillecode.scorpio.objects.character.Selection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CDisplayer {
    private final Player player;
    private final Character character;

    private int stage;
    private String content;
    private String current;
    private int place;
    private List<Selection> currentSelectionList;

    private boolean forceStop;

    private Component autoSpace;

    public CDisplayer(Player player, Character character) {
        this.player = player;
        this.character = character;
        this.setStage(1);
    }

    public void playText() {
        //sendOptionsText(player,player.getInventory().getHeldItemSlot());
        new BukkitRunnable() {
            @Override
            public void run() {
                //player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1F, 1.5F);
                if (place + 1 >= content.length() || forceStop) {
                    PLAYING_LIST.remove(player);
                    sendOptionsText(player, player.getInventory().getHeldItemSlot());
                    this.cancel();
                } else {
                    sendCompleteText();
                    current = current + content.charAt(++place);
                }
            }
        }.runTaskTimerAsynchronously(Scorpio.getInstance(), 0L, 1L);
    }

    private void sendCompleteText() {
        MiniMessage mm = MiniMessage.miniMessage();

        TextComponent textComponent = Component.text()
                .append(refreshComponent)
                .append(mm.deserialize("  <white><name>:", Placeholder.unparsed("name",character.getNPCName())))
                .appendNewline()
                .appendNewline()
                .append(mm.deserialize("  <white>\" <gray><ctext>_ <white>\"",Placeholder.unparsed("ctext",getCurrentText())))
                .appendNewline()
                .append(autoSpace)
                .appendNewline()
                .append(mm.deserialize("    <dark_gray>..."))
                .appendNewline()
                .build();
        player.sendMessage(textComponent);
        //player.sendRawMessage(String.format(" §f%s:", character.getNPCName())+"\n "+String.format("\n  §f\" §7%s_  §f\"", getCurrentText())+"\n "+"\n "+String.format("\n    %s", "§8...")+"\n ");
        //player.sendMessage(" ");
        //player.sendMessage(String.format("  §f\" §7%s_  §f\"", getCurrentText()));
        //player.sendMessage(" ");
        //player.sendMessage(" ");
        //player.sendMessage(String.format("    %s", "§8..."));
        //player.sendMessage(" ");
    }

    private String getCurrentText() {
        if (content.charAt(place) == '\n') {
            current = current+"    "+content.charAt(++place);
        }
        return current;
    }

    public void sendOptionsText(Player player, int i) {
        MiniMessage mm = MiniMessage.miniMessage();

        Component selectionComponent = Component.text("");
        for (int k = 0;k < currentSelectionList.size();k++) {
            if (i + 1 == currentSelectionList.get(k).getId()) {
                selectionComponent = selectionComponent.append(Component.newline()).append(mm.deserialize("  <gray>▶ <white><u><sel><reset> <dark_gray>(<gold><bold>F<reset><dark_gray>)",Placeholder.unparsed("sel",currentSelectionList.get(k).getText())));
            }
            else {
                selectionComponent = selectionComponent.append(Component.newline()).append(mm.deserialize("  <dark_gray>▶ <gray><sel>",Placeholder.unparsed("sel",currentSelectionList.get(k).getText())));
            }
        }


        TextComponent textComponent = Component.text()
                .append(refreshComponent)
                .append(mm.deserialize("  <white><name>:", Placeholder.unparsed("name",character.getNPCName())))
                .appendNewline()
                .appendNewline()
                .append(mm.deserialize("  <white>\" <gray><cont> <white>\"",Placeholder.unparsed("cont",getCurrentText())))
                .appendNewline()
                .appendNewline()
                .append(selectionComponent)
                .appendNewline()
                .build();
        player.sendMessage(textComponent);
    }

    public Character getCharacter() {
        return character;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        this.place = 0;
        int line = character.getEmergingTexts().get(stage - 1).getContent().size();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(character.getEmergingTexts().get(stage-1).getContent().get(0));
        for (int i = 1;i < line;i++) {
            stringBuilder.append("\n");
            stringBuilder.append(character.getEmergingTexts().get(stage-1).getContent().get(i));
        }
        this.autoSpace = getAutoSpaceComponent(line);
        this.content = stringBuilder.toString();
        this.currentSelectionList = character.getEmergingTexts().get(stage - 1).getSelections();
        this.current = String.valueOf(Objects.requireNonNull(content).charAt(place));
        this.forceStop = false;
    }

    public void forceStop() {
        this.forceStop = true;
    }

    private static Component getAutoSpaceComponent(int line) {
        Component autoSpaceComponent = Component.text("");
        for (int i = 0;i < line;i++) {
            autoSpaceComponent = autoSpaceComponent.appendNewline();
        }
        return autoSpaceComponent;
    }

    public static ArrayList<Player> PLAYING_LIST;
    public static ConcurrentHashMap<Player, CDisplayer> PLAYER_STAGE;

    private static Component refreshComponent;

    static {
        PLAYING_LIST = new ArrayList<>();
        PLAYER_STAGE = new ConcurrentHashMap<>();
        refreshComponent = Component.text("");
        for (int k = 0;k < 30;k++) {
            refreshComponent = refreshComponent.append(Component.newline());
        }
    }
}
