package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.item.MinerSpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockItems extends AbstractSkyblockRegistry {
    private final DeferredRegister<Item> registry = create(ForgeRegistries.ITEMS);
    public final RegistryObject<MinerSpawnEggItem> minerSpawnEgg = registry.register("miner_spawn_egg", MinerSpawnEggItem::new);

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
