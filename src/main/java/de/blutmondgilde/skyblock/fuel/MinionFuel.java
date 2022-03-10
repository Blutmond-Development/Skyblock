package de.blutmondgilde.skyblock.fuel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class MinionFuel implements IForgeRegistryEntry<MinionFuel> {
    @Getter
    private ResourceLocation registryName;
    @Getter
    private final Supplier<Item> asItem;

    /** Returns the amount of Ticks this fuel will be active. */
    public abstract long getDurationTicks();

    /**
     * Returns the amount of increased efficiency in percentage.
     * For example:
     * 0.5 -> 150%
     * -0.1 -> 90%
     */
    public abstract float getEfficiencyBoost(ItemStack stack, Level level);

    @Override
    public MinionFuel setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Override
    public Class<MinionFuel> getRegistryType() {
        return MinionFuel.class;
    }

    public boolean isConsumable() {
        return true;
    }
}
