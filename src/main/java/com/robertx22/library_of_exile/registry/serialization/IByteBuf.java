package com.robertx22.library_of_exile.registry.serialization;

import net.minecraft.network.PacketBuffer;

public interface IByteBuf<T> {

    T getFromBuf(PacketBuffer buf);

    void toBuf(PacketBuffer buf);
}
