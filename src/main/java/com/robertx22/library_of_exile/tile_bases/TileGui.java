package com.robertx22.library_of_exile.tile_bases;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.RequestTilePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.IFormattableTextComponent;

public abstract class TileGui<T extends BaseTileContainer, Tile extends BaseTile> extends ContainerScreen<T> {

    public Tile tile;
    Minecraft mc;

    public FontRenderer font = Minecraft.getInstance().font;

    public TileGui(T cont, PlayerInventory inv, IFormattableTextComponent text, Class<Tile> token) {
        super(cont, inv, text);

        this.mc = Minecraft.getInstance();

        if (cont.pos != null) {
            TileEntity en = Minecraft.getInstance().level.getBlockEntity(cont.pos);
            if (en != null) {
                if (token.isAssignableFrom(en.getClass())) {
                    this.tile = (Tile) en;
                }
            }
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw "inventory" text lol
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {

        if (tile != null) {
            if (mc.player.tickCount % 3 == 0) {
                Packets.sendToServer(new RequestTilePacket(tile.getBlockPos()));
            }
        }

        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);

    }
}
