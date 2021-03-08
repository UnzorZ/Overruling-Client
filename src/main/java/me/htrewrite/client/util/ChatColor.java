package me.htrewrite.client.util;

import me.htrewrite.client.HTRewrite;
import me.htrewrite.client.clickgui.components.buttons.settings.bettermode.BetterMode;

import java.util.ArrayList;

public class ChatColor {
    public static String parse(char c, String txt) { return txt.replaceAll(String.valueOf(c), "\u00a7"); }
    public static String prefix_parse(char c, String txt) { return parse(c, "&8&l[&b" + HTRewrite.NAME + "&8&l] &r" + txt); }
    public static String enumList(Enum[] enums) {
        StringBuilder list = new StringBuilder();
        for(int i = 0; i < enums.length; i++) {
            list.append(enums[i].name());
            if(enums.length-1!=i)
                list.append(", ");
        }
        return list.toString();
    }
    public static String betterList(BetterMode[] enums) {
        StringBuilder list = new StringBuilder();
        for(int i = 0; i < enums.length; i++) {
            list.append(enums[i].mode);
            if(enums.length-1!=i)
                list.append(", ");
        }
        return list.toString();
    }
    public static String[] enumToStringArray(Enum[] enums) {
        ArrayList<String> array = new ArrayList<>();
        for(Enum e : enums)
            array.add(e.name());
        return (String[])array.toArray();
    }
}