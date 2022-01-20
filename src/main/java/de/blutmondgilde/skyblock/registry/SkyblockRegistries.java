package de.blutmondgilde.skyblock.registry;

import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;

public class SkyblockRegistries {
    private static final ArrayList<AbstractSkyblockRegistry> registries = new ArrayList<>();
    public static final SkyblockEntities entities = new SkyblockEntities();
    public static final SkyblockItems items = new SkyblockItems();

    public static void init(IEventBus modBus) {
        //Register all elements to the Registry
        registries.forEach(registry -> registry.finish(modBus));
        //Clears the cache
        registries.clear();
    }

    static void registerRegistry(AbstractSkyblockRegistry registry) {
        registries.add(registry);
    }
}
