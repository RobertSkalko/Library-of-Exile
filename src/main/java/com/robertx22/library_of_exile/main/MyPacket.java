package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class MyPacket<T> {

    public abstract ResourceLocation getIdentifier();

    public abstract void loadFromData(PacketBuffer tag);

    public abstract void saveToData(PacketBuffer tag);

    public abstract void onReceived(ExilePacketContext ctx);

    public abstract MyPacket<T> newInstance();

    public final MyPacket loadFromDataUSETHIS(PacketBuffer buf) {

        MyPacket<T> data = newInstance();
        try {
            data.loadFromData(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(
                () -> {
                    try {
                        onReceived(new ExilePacketContext(ctx.get()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        ctx.get()
            .setPacketHandled(true);
    }

}
