package de.blutmondgilde.skyblock.client.event.handler;

import com.mojang.datafixers.util.Either;
import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.util.MessageUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.Color;
import java.util.Optional;

public class FuelToolTipHandler {
    private static final Color headline_color = new Color(75, 75, 75);
    private static final Color text_color = new Color(140, 140, 140);

    public static void onRenderToolTip(RenderTooltipEvent.GatherComponents e) {
        ItemStack itemStack = e.getItemStack();
        Optional.ofNullable(SkyblockRegistries.minionFuel.findByItem(itemStack.getItem())).ifPresent(fuel -> {
            e.getTooltipElements().add(Either.left(new TranslatableComponent("fuel.skyblock.headline").withStyle(Style.EMPTY.withBold(true).withColor(headline_color.getRGB()))));
            e.getTooltipElements().add(Either.left(new TranslatableComponent("fuel.skyblock.modifier",
                MessageUtils.floatToPercent(fuel.getEfficiencyBoost(itemStack, Skyblock.getProxy().getLevel())) + "%").withStyle(Style.EMPTY.withColor(text_color.getRGB()))));
            e.getTooltipElements().add(Either.left(new TranslatableComponent("fuel.skyblock.duration",
                MessageUtils.formatTicksToDuration(fuel.getDurationTicks())).withStyle(Style.EMPTY.withColor(text_color.getRGB()))));
        });
    }
}
