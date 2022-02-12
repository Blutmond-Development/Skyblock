package de.blutmondgilde.skyblock.client.screen.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.container.MinionMenu;
import de.blutmondgilde.skyblock.network.SkyblockNetwork;
import de.blutmondgilde.skyblock.network.toserver.CollectMinionItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MinionCollectAllButton extends Button {
    private static final ResourceLocation buttonBg = new ResourceLocation(Skyblock.MOD_ID, "textures/gui/button_small.png");
    private ItemStack chestIcon;

    public MinionCollectAllButton(MinionMenu minionMenu, Screen parent) {
        super(0, 0, 20, 20, new TextComponent(""), pButton -> {
            SkyblockNetwork.getInstance().sendToServer(new CollectMinionItems(minionMenu.getMinerEntity().getId()));
            parent.onClose();
        }, (pButton, pPoseStack, pMouseX, pMouseY) -> {
            //Title
            parent.renderTooltip(pPoseStack,
                new TranslatableComponent("skyblock.gui.minion.collect.title")
                    .withStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.DARK_GREEN)),
                pMouseX, pMouseY);
        });
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, buttonBg);

        blit(pPoseStack, x, y, 0, isHoveredOrFocused() ? 20 : 0, 20, 20, 20, 40);

        if (chestIcon == null) {
            chestIcon = new ItemStack(Items.CHEST);
        }

        Minecraft.getInstance().getItemRenderer().renderGuiItem(chestIcon, x + 2, y + 2);

        if (this.isHoveredOrFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
    }
}
