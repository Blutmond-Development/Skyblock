package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.entity.minion.farmer.WheatFarmerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.CoalMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.CobblestoneMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.CopperMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.DiamondMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.EmeraldMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.EndStoneMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.GlowstoneMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.GoldMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.GravelMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.HardStoneMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.IceMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.IronMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.LapisMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.MithrilMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.ObsidianMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.QuartzMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.RedstoneMinerEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.SandMinerEntity;
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
    public final RegistryObject<EntityType<CobblestoneMinerEntity>> cobblestoneMiner = minion("cobblestone_miner", CobblestoneMinerEntity::new);
    public final RegistryObject<EntityType<CopperMinerEntity>> copperMiner = minion("copper_miner", CopperMinerEntity::new);
    public final RegistryObject<EntityType<DiamondMinerEntity>> diamondMiner = minion("diamond_miner", DiamondMinerEntity::new);
    public final RegistryObject<EntityType<EmeraldMinerEntity>> emeraldMiner = minion("emerald_miner", EmeraldMinerEntity::new);
    public final RegistryObject<EntityType<EndStoneMinerEntity>> endStoneMiner = minion("end_stone_miner", EndStoneMinerEntity::new);
    public final RegistryObject<EntityType<GlowstoneMinerEntity>> glowstoneMiner = minion("glowstone_miner", GlowstoneMinerEntity::new);
    public final RegistryObject<EntityType<GoldMinerEntity>> goldMiner = minion("gold_miner", GoldMinerEntity::new);
    public final RegistryObject<EntityType<GravelMinerEntity>> gravelMiner = minion("gravel_miner", GravelMinerEntity::new);
    public final RegistryObject<EntityType<HardStoneMinerEntity>> hardStoneMiner = minion("hard_stone_miner", HardStoneMinerEntity::new);
    public final RegistryObject<EntityType<IceMinerEntity>> iceMiner = minion("ice_miner", IceMinerEntity::new);
    public final RegistryObject<EntityType<IronMinerEntity>> ironMiner = minion("iron_miner", IronMinerEntity::new);
    public final RegistryObject<EntityType<LapisMinerEntity>> lapisMiner = minion("lapis_miner", LapisMinerEntity::new);
    public final RegistryObject<EntityType<MithrilMinerEntity>> mithrilMiner = minion("mithril_miner", MithrilMinerEntity::new);
    public final RegistryObject<EntityType<ObsidianMinerEntity>> obsidianMiner = minion("obsidian_miner", ObsidianMinerEntity::new);
    public final RegistryObject<EntityType<QuartzMinerEntity>> quartzMiner = minion("quartz_miner", QuartzMinerEntity::new);
    public final RegistryObject<EntityType<RedstoneMinerEntity>> redstoneMiner = minion("redstone_miner", RedstoneMinerEntity::new);
    public final RegistryObject<EntityType<SandMinerEntity>> sandMiner = minion("sand_miner", SandMinerEntity::new);
    public final RegistryObject<EntityType<WheatFarmerEntity>> wheatFarmer = minion("wheat_farmer", WheatFarmerEntity::new);

    private <T extends MinionEntity> RegistryObject<EntityType<T>> minion(String name, EntityType.EntityFactory<T> factory) {
        return registry.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(0.6F, 1.95F).build(new SkyblockResourceLocation(name).toString()));
    }

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
