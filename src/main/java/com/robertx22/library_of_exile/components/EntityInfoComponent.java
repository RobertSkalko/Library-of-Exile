package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.main.Components;
import com.robertx22.library_of_exile.utils.LoadSave;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class EntityInfoComponent {

    public static IEntityInfo get(LivingEntity entity) {
        return Components.INSTANCE.ENTITY_INFO.get(entity);
    }

    private static final String DMG_STATS = "dmg_stats";
    private static final String SPAWN_POS = "spawn_pos";

    public interface IEntityInfo extends Component {

        EntityDmgStatsData getDamageStats();

        BlockPos getSpawnPos();

        void setBlockPosOnSpawn();
    }

    public static class DefaultImpl implements IEntityInfo {

        LivingEntity entity;

        EntityDmgStatsData dmgStats = new EntityDmgStatsData();

        private BlockPos spawnPos;

        public DefaultImpl(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public CompoundTag toTag(CompoundTag nbt) {

            try {
                if (dmgStats != null) {
                    LoadSave.Save(dmgStats, nbt, DMG_STATS);
                }
                if (spawnPos != null) {
                    nbt.putLong(SPAWN_POS, spawnPos.asLong());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return nbt;

        }

        @Override
        public void fromTag(CompoundTag nbt) {

            try {
                this.dmgStats = LoadSave.Load(EntityDmgStatsData.class, new EntityDmgStatsData(), nbt, DMG_STATS);
                if (dmgStats == null) {
                    dmgStats = new EntityDmgStatsData();
                }
                this.spawnPos = BlockPos.fromLong(nbt.getLong(SPAWN_POS));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public BlockPos getSpawnPos() {
            if (spawnPos != null) {
                return spawnPos;
            }
            return entity.getBlockPos();
        }

        @Override
        public void setBlockPosOnSpawn() {
            if (spawnPos == null || (spawnPos.getX() == 0 && spawnPos.getY() == 0 && spawnPos.getZ() == 0)) {
                this.spawnPos = entity.getBlockPos();
            }
        }

        @Override
        public EntityDmgStatsData getDamageStats() {
            return dmgStats;
        }
    }

}
