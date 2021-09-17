package com.robertx22.library_of_exile.tile_bases;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.RequestTilePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public abstract class BaseTileGui<Tile extends TileEntity> extends Screen {

    public Tile tile;
    public Minecraft mc;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;

    protected int xSize = 176;
    protected int ySize = 166;

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

    }

    public BaseTileGui(Class<Tile> token, BlockPos pos, int x, int y) {
        super(new StringTextComponent(""));

        xSize = x;
        ySize = y;

        this.mc = Minecraft.getInstance();

        if (pos != null) {
            TileEntity en = Minecraft.getInstance().level.getBlockEntity(pos);
            if (en != null) {
                if (token.isAssignableFrom(en.getClass())) {
                    this.tile = (Tile) en;
                }
            }
        }
    }

    int ticks;

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {

        ticks++;

        if (tile != null) {
            if (ticks % 10 == 0 || ticks < 2) {
                Packets.sendToServer(new RequestTilePacket(tile.getBlockPos()));
            }
        }

        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);

    }

}
