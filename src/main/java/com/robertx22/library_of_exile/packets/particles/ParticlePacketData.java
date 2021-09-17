package com.robertx22.library_of_exile.packets.particles;

import com.robertx22.library_of_exile.utils.RGB;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;

@Storable
public class ParticlePacketData {

    private ParticlePacketData() {

    }

    public static ParticlePacketData empty() {
        return new ParticlePacketData();
    }

    @Store
    public double x = 1;
    @Store
    public double y = 1;
    @Store
    public double z = 1;

    @Store
    public boolean isVecPos = false;

    @Store
    public double mx = 1;
    @Store
    public double my = 1;
    @Store
    public double mz = 1;

    @Store
    public ParticleEnum type;

    @Store
    public float radius = 1;

    @Store
    public int amount = 1;

    @Store
    public RGB color;

    @Store
    public String particleID;

    public <T extends IParticleData> IParticleData getParticleType() {
        ParticleType<T> particleType = (ParticleType<T>) Registry.PARTICLE_TYPE.get(new ResourceLocation(particleID));

        if (particleType instanceof IParticleData) {
            return (IParticleData) particleType;
        } else return ParticleTypes.CRIT;
    }

    public ParticlePacketData motion(Vector3d v) {
        mx = v.x;
        my = v.y;
        mz = v.z;
        return this;
    }

    public ParticlePacketData type(ParticleType type) {
        this.particleID = Registry.PARTICLE_TYPE.getKey(type)
            .toString();
        return this;
    }

    public ParticlePacketData amount(int num) {
        this.amount = num;
        return this;
    }

    public ParticlePacketData radius(double rad) {
        this.radius = (float) rad;
        return this;
    }

    public ParticlePacketData radius(float rad) {
        this.radius = rad;
        return this;
    }

    public ParticlePacketData color(RGB color) {
        this.color = color;
        return this;
    }

    public Vector3d getPos() {
        return new Vector3d(x, y, z);
    }

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public ParticlePacketData(Vector3d pos, ParticleEnum type) {
        x = pos.x();
        y = pos.y();
        z = pos.z();
        this.isVecPos = true;
        this.type = type;
    }

    public ParticlePacketData(BlockPos pos, ParticleEnum type) {
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
        this.type = type;
    }
}
