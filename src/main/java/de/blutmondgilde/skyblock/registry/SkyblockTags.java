package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.Skyblock;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class SkyblockTags {
    public static final Tag.Named<Item> MINION_SPECIAL_UPGRADES = ItemTags.bind(Skyblock.MOD_ID + ":minion_special_upgrades");
    public static final Tag.Named<Item> MINION_UPGRADES = ItemTags.bind(Skyblock.MOD_ID + ":minion_upgrades");
}
