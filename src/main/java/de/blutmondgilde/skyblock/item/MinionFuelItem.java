package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import de.blutmondgilde.skyblock.util.ItemRarity;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MinionFuelItem extends Item {
    private static final Color valueColor = new Color(52, 255, 64);

    @Getter
    private final int maxDuration;
    @Getter
    private final float modifier;
    @Getter
    private final ItemRarity rarity;

    public MinionFuelItem(int maxDuration, float modifier, ItemRarity rarity) {
        super(new Properties().tab(SkyblockTabs.MINION_UPGRADES));
        this.modifier = modifier;
        this.maxDuration = maxDuration;
        this.rarity = rarity;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(new TranslatableComponent("skyblock.text.minion.fuel",
            new TextComponent((modifier * 100) + "%").withStyle(Style.EMPTY.withColor(valueColor.getRGB())),
            durationTextComponent()));
        rarity.applyTo(tooltip);
    }

    private MutableComponent durationTextComponent() {
        MutableComponent component = new TextComponent("");

        if (this.maxDuration != -1) {
            List<MutableComponent> messageParts = new ArrayList<>();
            int remainingTime = maxDuration;
            //Add Days
            if (remainingTime >= 60 * 24) {
                int days = remainingTime / (60 * 24);
                if (days == 1) {
                    //Day
                    messageParts.add(new TranslatableComponent("skyblock.text.time.day", textOf(days)));
                } else {
                    //Days
                    messageParts.add(new TranslatableComponent("skyblock.text.time.days", textOf(days)));
                }
                remainingTime -= days * 60 * 24;
            }

            //Add Hours
            if (remainingTime >= 60) {
                int hours = remainingTime / 60;
                if (hours == 1) {
                    //Hour
                    messageParts.add(new TranslatableComponent("skyblock.text.time.hour", textOf(hours)));
                } else {
                    //Hours
                    messageParts.add(new TranslatableComponent("skyblock.text.time.hours", textOf(hours)));
                }
                remainingTime -= hours * 60;
            }

            //Add Minutes
            if (remainingTime > 0) {
                if (remainingTime == 1) {
                    //Minute
                    messageParts.add(new TranslatableComponent("skyblock.text.time.minute", textOf(remainingTime)));
                } else {
                    //Minutes
                    messageParts.add(new TranslatableComponent("skyblock.text.time.minutes", textOf(remainingTime)));
                }
            }

            switch (messageParts.size()) {
                case 3 -> {
                    component.append(messageParts.get(0));
                    component.append(" ");
                    component.append(messageParts.get(1));
                    component.append(" ");
                    component.append(new TranslatableComponent("skyblock.text.time.and"));
                    component.append(" ");
                    component.append(messageParts.get(2));
                }
                case 2 -> {
                    component.append(messageParts.get(0));
                    component.append(" ");
                    component.append(new TranslatableComponent("skyblock.text.time.and"));
                    component.append(" ");
                    component.append(messageParts.get(1));
                }
                case 1 -> component.append(messageParts.get(0));
            }
        }

        return component.append("!");
    }

    private MutableComponent textOf(int value) {
        return new TextComponent(String.valueOf(value)).withStyle(Style.EMPTY.withColor(valueColor.getRGB()));
    }
}
