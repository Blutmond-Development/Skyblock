package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.CoalMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.CopperMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.DiamondMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.EmeraldMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.GoldMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.IronMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.LapisLazuliMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.RedstoneMinerEntity;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockEntities extends AbstractSkyblockRegistry {
    private final DeferredRegister<EntityType<?>> registry = create(ForgeRegistries.ENTITIES);
    public final RegistryObject<EntityType<CoalMinerEntity>> coalMiner = minion("coal_miner", CoalMinerEntity::new);
    public final RegistryObject<EntityType<CopperMinerEntity>> copperMiner = minion("copper_miner", CopperMinerEntity::new);
    public final RegistryObject<EntityType<LapisLazuliMinerEntity>> lapisMiner = minion("lapis_miner", LapisLazuliMinerEntity::new);
    public final RegistryObject<EntityType<IronMinerEntity>> ironMiner = minion("iron_miner", IronMinerEntity::new);
    public final RegistryObject<EntityType<GoldMinerEntity>> goldMiner = minion("gold_miner", GoldMinerEntity::new);
    public final RegistryObject<EntityType<RedstoneMinerEntity>> redstoneMiner = minion("redstone_miner", RedstoneMinerEntity::new);
    public final RegistryObject<EntityType<DiamondMinerEntity>> diamondMiner = minion("diamond_miner", DiamondMinerEntity::new);
    public final RegistryObject<EntityType<EmeraldMinerEntity>> emeraldMiner = minion("emerald_miner", EmeraldMinerEntity::new);

    private <T extends MinionEntity> RegistryObject<EntityType<T>> minion(String name, EntityType.EntityFactory<T> factory) {
        return registry.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(0.6F, 1.95F).build(new SkyblockResourceLocation(name).toString()));
    }

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
