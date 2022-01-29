package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.container.MinerMinionMenu;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockContainer extends AbstractSkyblockRegistry {
    private final DeferredRegister<MenuType<?>> registry = create(ForgeRegistries.CONTAINERS);
    public final RegistryObject<MenuType<MinerMinionMenu>> minerMinionMenu = registry.register("miner_minion_menu", () -> IForgeMenuType.create(
        (windowId, inv, data) -> {
            Entity entity = Skyblock.getProxy().getLevel().getEntity(data.readInt());

            if (entity instanceof MinerEntity minerEntity) {
                return new MinerMinionMenu(windowId, inv, minerEntity);
            } else {
                throw new IllegalStateException("Tried to open a miner minion inventory with a non miner minion entity!");
            }
        }));

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
