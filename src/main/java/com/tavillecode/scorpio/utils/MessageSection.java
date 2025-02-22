package com.tavillecode.scorpio.utils;

import org.bukkit.entity.Player;

public class MessageSection {
    private final static String heading = "[Scorpio]";
    public static void EnableMessages() {
        System.out.print("\n" + heading + " 天蝎座没什么好说的");
        System.out.print("\n" + heading + " 作者: 凉呈哟");
        System.out.print("\n" + heading + " 工作室: TavilleTribe\n");
    }
    public static void HelpMessages(Player p) {
        p.sendMessage("§8§m                                                                            ");
        p.sendMessage(" ");
        p.sendMessage("   §f“勉强还行”");
        p.sendMessage("      §f“这个插件”");
        p.sendMessage(" ");
        p.sendMessage("§7- §fAuthor: [§nTavilleTribe.凉呈哟§r]");
        p.sendMessage("§7- §fVersion: [§n1.0§r]");
        p.sendMessage(" ");
        p.sendMessage("§7/§fscorpio reload §7##重新读取所有配置");
        p.sendMessage(" ");
        p.sendMessage("§8§m                                                                            ");
    }
}
