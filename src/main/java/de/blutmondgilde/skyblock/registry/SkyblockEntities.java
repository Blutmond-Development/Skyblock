package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.entity.minion.miner.CoalMinerEntity;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockEntities extends AbstractSkyblockRegistry {
    private final DeferredRegister<EntityType<?>> registry = create(ForgeRegistries.ENTITIES);
    public final RegistryObject<EntityType<CoalMinerEntity>> coalMiner = registry.register("coal_miner", () -> EntityType.Builder.of(CoalMinerEntity::new,
        MobCategory.CREATURE).sized(0.6F, 1.95F).build(new SkyblockResourceLocation("coal_miner").toString()));


    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
