package de.blutmondgilde.skyblock.util;

import de.blutmondgilde.skyblock.Skyblock;
import net.minecraft.resources.ResourceLocation;

public class SkyblockResourceLocation extends ResourceLocation {
    public SkyblockResourceLocation(String pPath) {
        super(Skyblock.MOD_ID, pPath);
    }
}
