package de.blutmondgilde.skyblock.util;

import de.blutmondgilde.skyblock.Skyblock;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.awt.Color;
import java.util.List;

@RequiredArgsConstructor
public enum ItemRarity {
    COMMON(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.common").withStyle(Style.EMPTY.withBold(true).withColor(new Color(255, 255, 255).getRGB()))),
    UNCOMMON(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.uncommon").withStyle(Style.EMPTY.withBold(true).withColor(new Color(0, 255, 13).getRGB()))),
    RARE(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.rare").withStyle(Style.EMPTY.withBold(true).withColor(new Color(1, 141, 208).getRGB()))),
    EPIC(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.epic").withStyle(Style.EMPTY.withBold(true).withColor(new Color(141, 0, 152).getRGB()))),
    LEGENDARY(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.epic").withStyle(Style.EMPTY.withBold(true).withColor(new Color(173, 114, 1).getRGB()))),
    MYTHIC(new TranslatableComponent(Skyblock.MOD_ID + ".ratiry.epic").withStyle(Style.EMPTY.withBold(true).withColor(new Color(255, 10, 188).getRGB())));

    private final MutableComponent component;

    public void applyTo(List<Component> components) {
        components.add(new TextComponent(""));
        components.add(this.component);
    }
}
