package de.blutmondgilde.skyblock.util;

import de.blutmondgilde.skyblock.fuel.MinionFuel;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public class FuelTimer implements INBTSerializable<CompoundTag> {
    @Getter
    private Component lastFuel = new TranslatableComponent("skyblock.gui.minion.fuel.none");
    @Getter
    private float currentModifier = 0F;
    @Getter
    private long remainingTicks = 0;

    public void tick() {
        if (remainingTicks > 0) {
            remainingTicks--;
            //Reset Stats
            if (remainingTicks == 0) {
                currentModifier = 0F;
            }
        }
    }

    /**
     * Updates the current fuel stats.
     *
     * @return true = consume ItemStack, false = don't consume ItemStack
     */
    public boolean setFuel(MinionFuel fuel, ItemStack stack, Level level) {
        if (remainingTicks == 0) {
            this.remainingTicks = fuel.getDurationTicks();
            this.currentModifier = fuel.getEfficiencyBoost(stack, level);
            this.lastFuel = stack.getDisplayName();
            return true;
        } else {
            return false;
        }
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("modifier", this.currentModifier);
        tag.putLong("duration", this.remainingTicks);
        tag.putString("name", Component.Serializer.toJson(this.lastFuel));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.currentModifier = nbt.getFloat("modifier");
        this.remainingTicks = nbt.getLong("duration");
        this.lastFuel = Component.Serializer.fromJson(nbt.getString("name"));
    }

    public boolean hasFuel() {
        return currentModifier > 0F || remainingTicks > 0;
    }
}
