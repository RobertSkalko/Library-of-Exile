package com.robertx22.library_of_exile.packets.registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.robertx22.library_of_exile.main.LibraryOfExile;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.ListStringData;
import com.robertx22.library_of_exile.registry.RegistryPackets;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.Watch;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class RegistryPacket extends MyPacket<RegistryPacket> {
    public static Identifier ID = new Identifier(Ref.MODID, "reg");

    public static final JsonParser PARSER = new JsonParser();

    ExileRegistryType type;
    ListStringData data;

    public RegistryPacket() {

    }

    public <T extends JsonExileRegistry> RegistryPacket(ExileRegistryType type, ListStringData data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {

        try {
            type = ExileRegistryType.get(tag.readString(30));

            if (LibraryOfExile.runDevTools()) {
                //System.out.print("\n Gson packet " + type.name() + " is " + tag.readableBytes() + " bytes big\n");
            }
            NbtCompound nbt = tag.readNbt();

            data = LoadSave.Load(ListStringData.class, new ListStringData(), nbt, "data");

        } catch (Exception e) {
            System.out.println("Failed reading Age of Exile packet to bufferer.");
            e.printStackTrace();
        }

    }

    @Override
    public void saveToData(PacketByteBuf tag) {

        try {
            Watch watch = new Watch().min(8000);
            tag.writeString(type.id, 30);
            NbtCompound nbt = new NbtCompound();

            LoadSave.Save(data, nbt, "data");

            tag.writeNbt(nbt);
            watch.print("Writing gson packet for " + this.type.id + " ");
        } catch (Exception e) {
            System.out.println("Failed saving " + type.id + " Age of Exile packet to bufferer.");
            e.printStackTrace();
        }

    }

    @Override
    public void onReceived(PacketContext ctx) {

        if (data.getList()
            .isEmpty()) {
            throw new RuntimeException("Registry list sent from server is empty!");
        }

        List<JsonObject> set = RegistryPackets.get(type);

        data.getList()
            .forEach(x -> {
                try {
                    JsonObject json = (JsonObject) PARSER.parse(x);
                    set.add(json);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();

                }
            });

    }

    @Override
    public MyPacket<RegistryPacket> newInstance() {
        return new RegistryPacket();
    }
}