package com.robertx22.library_of_exile.utils;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class CLOC {

    public static String translate(Text s) {
        return I18n.translate(s.getString()
            .replaceAll("%", "PERCENT"))
            .replaceAll("PERCENT", "%");

    }

    public static MutableText base(String s) {
        if (s.isEmpty()) {
            return new LiteralText("");
        } else {
            return new TranslatableText(s);
        }
    }

    public static MutableText blank(String string) {
        return base(string);
    }

}
