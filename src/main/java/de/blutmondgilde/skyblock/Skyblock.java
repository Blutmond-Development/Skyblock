package de.blutmondgilde.skyblock;

import de.blutmondgilde.skyblock.proxy.ClientProxy;
import de.blutmondgilde.skyblock.proxy.CommonProxy;
import de.blutmondgilde.skyblock.proxy.ServerProxy;
import lombok.Getter;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Skyblock.MOD_ID)
public class Skyblock {
    public static final String MOD_ID = "skyblock";
    @Getter
    private static final Logger Logger = LogManager.getLogger();
    @Getter
    private static Skyblock instance;
    private final CommonProxy proxy;

    public Skyblock() {
        instance = this;
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    /**
     * Returns the Proxy instance
     */
    public static CommonProxy getProxy() {
        return instance.proxy;
    }
}
