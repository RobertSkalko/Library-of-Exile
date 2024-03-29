package com.robertx22.library_of_exile.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class HelpButton extends ImageButton {

    static ResourceLocation ID = new ResourceLocation(Ref.MODID, "textures/gui/spell_help.png");
    List<ITextComponent> tooltip;

    public HelpButton(List<ITextComponent> tooltip, int x, int y) {
        super(x, y, 20, 20, 0, 0, 1, ID,
            action -> {

            });
        this.tooltip = tooltip;
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, 20, 20, x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        if (isInside(mouseX, mouseY)) {
            GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
        }
    }
}