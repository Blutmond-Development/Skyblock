package de.blutmondgilde.skyblock.client.screen.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.network.SkyblockNetwork;
import de.blutmondgilde.skyblock.network.toserver.RequestMinionLevelUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MinionLevelUpButton extends Button {
    private static final ResourceLocation buttonImage = new ResourceLocation(Skyblock.MOD_ID, "textures/gui/level_up_button.png");
    private static final ResourceLocation buttonBg = new ResourceLocation(Skyblock.MOD_ID, "textures/gui/button_small.png");

    public MinionLevelUpButton(MinionEntity minionEntity, Inventory playerInventory, Screen parent) {
        super(0, 0, 20, 20, new TextComponent(""), pButton -> {
            SkyblockNetwork.getInstance().sendToServer(new RequestMinionLevelUp(minionEntity.getId()));
            parent.onClose();
        }, (pButton, pPoseStack, pMouseX, pMouseY) -> {
            List<FormattedCharSequence> toolTip = new ArrayList<>();
            //Title
            MutableComponent currentLvl = new TextComponent(String.valueOf(minionEntity.getMinionLevel())).withStyle(Style.EMPTY.withBold(true).withColor(new Color(1, 203, 15).getRGB()));
            MutableComponent newLev = new TextComponent(String.valueOf(minionEntity.getMinionLevel() + 1)).withStyle(Style.EMPTY.withBold(true).withColor(new Color(1, 203, 15).getRGB()));
            toolTip.addAll(Minecraft.getInstance().font.split(
                new TranslatableComponent("skyblock.gui.minion.level.up.0", currentLvl, newLev).withStyle(Style.EMPTY.withColor(new Color(0, 133, 11).getRGB())),
                150));
            //Desc
            toolTip.addAll(Minecraft.getInstance().font.split(new TranslatableComponent("skyblock.gui.minion.level.up.1"), 150));
            toolTip.addAll(Minecraft.getInstance().font.split(new TextComponent(" "), 150));
            //Required Items
            toolTip.addAll(minionEntity.getLevelUpMaterialList(playerInventory).stream()
                .map(mutableComponent -> Minecraft.getInstance().font.split(mutableComponent, 150))
                .flatMap(Collection::stream)
                .toList());

            parent.renderTooltip(pPoseStack, toolTip, pMouseX, pMouseY);
        });
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, buttonBg);

        blit(pPoseStack, x, y, 0, isHoveredOrFocused() ? 20 : 0, 20, 20, 20, 40);


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, buttonImage);

        float scale = 1 / 5F;
        float unscale = 5F;
        pPoseStack.scale(scale, scale, scale);
        blit(pPoseStack, Math.round((x + 4) * unscale), Math.round((y + 4) * unscale), 0, 64, 64, 64, 64, 64, 64);
        pPoseStack.scale(unscale, unscale, unscale);

        if (this.isHoveredOrFocused()) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
    }
}
