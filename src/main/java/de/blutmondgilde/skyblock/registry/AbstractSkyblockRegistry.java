package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.Skyblock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractSkyblockRegistry {
    public AbstractSkyblockRegistry() {
        SkyblockRegistries.registerRegistry(this);
    }

    abstract void register(IEventBus modBus);

    protected static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, Skyblock.MOD_ID);
    }

    void finish(IEventBus modBus) {
        register(modBus);
    }
}
