package com.robertx22.library_of_exile.utils;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CLOC {

    public static String translate(ITextComponent s) {
        return I18n.get(s.getString()
                .replaceAll("%", "PERCENT"))
            .replaceAll("PERCENT", "%");

    }

    public static IFormattableTextComponent base(String s) {
        if (s.isEmpty()) {
            return new StringTextComponent("");
        } else {
            return new TranslationTextComponent(s);
        }
    }

    public static IFormattableTextComponent blank(String string) {
        return base(string);
    }

}
