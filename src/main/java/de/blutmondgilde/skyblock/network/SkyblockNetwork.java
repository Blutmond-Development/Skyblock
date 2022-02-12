package de.blutmondgilde.skyblock.network;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.network.toserver.CollectMinionItems;
import de.blutmondgilde.skyblock.network.toserver.RequestMinionLevelUp;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SkyblockNetwork {
    private static final String PROTOCOL_VERSION = String.valueOf(1);
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Skyblock.MOD_ID, "main"),
        () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static SimpleChannel getInstance() {
        return INSTANCE;
    }

    public static void registerPackets() {
        int id = 1;
        getInstance().registerMessage(id++, RequestMinionLevelUp.class, RequestMinionLevelUp::toBytes, RequestMinionLevelUp::new, RequestMinionLevelUp::handle);
        getInstance().registerMessage(id++, CollectMinionItems.class, CollectMinionItems::toBytes, CollectMinionItems::new, CollectMinionItems::handle);
    }
}
