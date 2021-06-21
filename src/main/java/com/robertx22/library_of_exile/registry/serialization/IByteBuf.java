package com.robertx22.library_of_exile.registry.serialization;

import net.minecraft.network.PacketByteBuf;

public interface IByteBuf<T> {

    T getFromBuf(PacketByteBuf buf);

    void toBuf(PacketByteBuf buf);
}
