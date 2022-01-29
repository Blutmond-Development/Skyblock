package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.fuel.CoalBlockFuel;
import de.blutmondgilde.skyblock.fuel.CoalFuel;
import de.blutmondgilde.skyblock.fuel.MinionFuel;
import de.blutmondgilde.skyblock.fuel.MinionFuelWrapper;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class SkyblockMinionFuelRegistry extends AbstractSkyblockRegistry {
    private final DeferredRegister<MinionFuel> registry = DeferredRegister.create(MinionFuel.class, Skyblock.MOD_ID);
    public RegistryObject<CoalFuel> coal = registry.register("coal", CoalFuel::new);
    public RegistryObject<CoalBlockFuel> coalBlock = registry.register("coal_block", CoalBlockFuel::new);
    public RegistryObject<MinionFuelWrapper> efficientCharCoal = registry.register("efficient_char_coal", () -> MinionFuelWrapper.of(SkyblockRegistries.items.efficientCharCoalItem.get()));
    public RegistryObject<MinionFuelWrapper> efficientCoal = registry.register("efficient_coal", () -> MinionFuelWrapper.of(SkyblockRegistries.items.efficientCoalItem.get()));
    public RegistryObject<MinionFuelWrapper> tastyBread = registry.register("tasty_break", () -> MinionFuelWrapper.of(SkyblockRegistries.items.tastyBreadItem.get()));

    public SkyblockMinionFuelRegistry() {
        super();
        registry.makeRegistry("minion_fuel", RegistryBuilder::new);
    }

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }

    @Nullable
    public MinionFuel findByItem(Item item) {
        return RegistryObject.of(item.getRegistryName(), () -> MinionFuel.class).get();
    }
}
