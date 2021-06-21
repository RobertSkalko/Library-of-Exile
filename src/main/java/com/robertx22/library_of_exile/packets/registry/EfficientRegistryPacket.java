package com.robertx22.library_of_exile.packets.registry;

import com.google.common.collect.Lists;
import com.robertx22.library_of_exile.main.LibraryOfExile;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.IByteBuf;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class EfficientRegistryPacket<T extends IByteBuf & JsonExileRegistry> extends MyPacket<EfficientRegistryPacket> {
    public static Identifier ID = new Identifier(Ref.MODID, "eff_reg");
    private List<T> items;

    ExileRegistryType type;

    public EfficientRegistryPacket() {

    }

    public EfficientRegistryPacket(ExileRegistryType type, List<T> list) {
        this.type = type;
        this.items = list;
    }

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void loadFromData(PacketByteBuf buf) {

        this.type = ExileRegistryType.get(buf.readString(30));

        if (LibraryOfExile.runDevTools()) {
            //System.out.print("\n Eff packet " + type.name() + " is " + buf.readableBytes() + " bytes big \n");
        }

        IByteBuf<T> serializer = (IByteBuf<T>) type.getSerializer();

        this.items = Lists.newArrayList();

        int i = buf.readVarInt();

        for (int j = 0; j < i; ++j) {
            this.items.add(serializer.getFromBuf(buf));
        }

    }

    @Override
    public void saveToData(PacketByteBuf buf) {

        buf.writeString(type.id, 30);
        buf.writeVarInt(this.items.size());
        items.forEach(x -> x.toBuf(buf));

    }

    @Override
    public void onReceived(PacketContext ctx) {

        ExileRegistryContainer reg = Database.getRegistry(type);

        reg.unregisterAllEntriesFromDatapacks();

        items.forEach(x -> x.registerToExileRegistry());

        System.out.println("Efficient " + type.id + " reg load on client success with: " + reg.getSize() + " entries.");

    }

    @Override
    public MyPacket<EfficientRegistryPacket> newInstance() {
        return new EfficientRegistryPacket();
    }
}