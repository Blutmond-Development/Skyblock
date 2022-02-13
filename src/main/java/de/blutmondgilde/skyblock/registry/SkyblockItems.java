package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.item.InstructionsOfTheMasterItem;
import de.blutmondgilde.skyblock.item.MinerSpawnEggItem;
import de.blutmondgilde.skyblock.item.PickedMinionItem;
import de.blutmondgilde.skyblock.item.block.EfficientCoalBlockItem;
import de.blutmondgilde.skyblock.item.fuel.EfficientCharCoalItem;
import de.blutmondgilde.skyblock.item.fuel.EfficientCoalItem;
import de.blutmondgilde.skyblock.item.fuel.TastyBreadItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockItems extends AbstractSkyblockRegistry {
    private final DeferredRegister<Item> registry = create(ForgeRegistries.ITEMS);
    public final RegistryObject<MinerSpawnEggItem> minerSpawnEgg = registry.register("miner_spawn_egg", MinerSpawnEggItem::new);
    public final RegistryObject<EfficientCharCoalItem> efficientCharCoalItem = registry.register("efficient_char_coal", EfficientCharCoalItem::new);
    public final RegistryObject<EfficientCoalItem> efficientCoalItem = registry.register("efficient_coal", EfficientCoalItem::new);
    public final RegistryObject<TastyBreadItem> tastyBreadItem = registry.register("tasty_bread", TastyBreadItem::new);
    public final RegistryObject<EfficientCoalBlockItem> efficientCoalBlock = registry.register("efficient_coal_block", EfficientCoalBlockItem::new);
    public final RegistryObject<InstructionsOfTheMasterItem> instructionsOfTheMaster = registry.register("instructions_of_the_master", InstructionsOfTheMasterItem::new);
    public final RegistryObject<PickedMinionItem> pickedMinion = registry.register("picked_minion", PickedMinionItem::new);

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
