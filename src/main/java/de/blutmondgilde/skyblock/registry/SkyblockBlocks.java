package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.block.EfficientCoalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockBlocks extends AbstractSkyblockRegistry {
    private final DeferredRegister<Block> registry = create(ForgeRegistries.BLOCKS);
    public final RegistryObject<EfficientCoalBlock> efficientCoalBlock = registry.register("efficient_coal_block", EfficientCoalBlock::new);

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
