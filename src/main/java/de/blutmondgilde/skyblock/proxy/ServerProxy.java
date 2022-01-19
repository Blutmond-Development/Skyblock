package de.blutmondgilde.skyblock.proxy;

public class ServerProxy extends CommonProxy {
    @Override
    public boolean isClient() {
        return false;
    }
}
