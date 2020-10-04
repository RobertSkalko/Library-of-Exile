package com.robertx22.library_of_exile.main;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class MyPacket<T> implements PacketConsumer {

    public abstract Identifier getIdentifier();

    public abstract void loadFromData(PacketByteBuf tag);

    public abstract void saveToData(PacketByteBuf tag);

    public abstract void onReceived(PacketContext ctx);

    public abstract MyPacket<T> newInstance();

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {

        MyPacket<T> data = newInstance();

        try {
            data.loadFromData(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ctx.getTaskQueue()
            .execute(() -> {
                try {
                    data.onReceived(ctx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
