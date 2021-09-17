package com.robertx22.library_of_exile.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {

    public static void render16Icon(MatrixStack matrix, ResourceLocation tex, int x, int y) {
        Minecraft.getInstance()
            .getTextureManager()
            .bind(tex);

        AbstractGui.blit(matrix, x, y, 0, 0, 16, 16, 16, 16);
    }

    public static void renderStack(ItemStack stack, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.enableDepthTest();
        mc.getItemRenderer()
            .renderAndDecorateFakeItem(stack, x, y);
        mc.getItemRenderer()
            .renderGuiItemDecorations(mc.font, stack, x, y);

        RenderSystem.disableDepthTest();
    }
}