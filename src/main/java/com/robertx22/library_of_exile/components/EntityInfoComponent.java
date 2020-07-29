package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.main.Components;
import com.robertx22.library_of_exile.utils.LoadSave;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public class EntityInfoComponent {

    public static IEntityInfo get(LivingEntity entity) {
        return Components.INSTANCE.ENTITY_INFO.get(entity);
    }

    private static final String DMG_STATS = "dmg_stats";

    public interface IEntityInfo extends Component {

        EntityDmgStatsData getDamageStats();

    }

    public static class DefaultImpl implements IEntityInfo {

        LivingEntity entity;

        EntityDmgStatsData dmgStats = new EntityDmgStatsData();

        public DefaultImpl(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public CompoundTag toTag(CompoundTag nbt) {

            if (dmgStats != null) {
                LoadSave.Save(dmgStats, nbt, DMG_STATS);
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public EntityDmgStatsData getDamageStats() {
            return dmgStats;
        }
    }

}
