package de.blutmondgilde.skyblock.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.client.screen.button.MinionCollectAllButton;
import de.blutmondgilde.skyblock.client.screen.button.MinionLevelUpButton;
import de.blutmondgilde.skyblock.container.MinionMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.GuiUtils;

import java.awt.Color;

public class MinerMinionContainerScreen extends AbstractContainerScreen<MinionMenu> {
    private static final ResourceLocation background = new ResourceLocation(Skyblock.MOD_ID, "textures/gui/miner_gui.png");
    private int minionLevel = 1;
    private final MinionLevelUpButton levelUpButton;
    private final MinionCollectAllButton collectAllButton;
    private Component levelMessage;
    private int levelMessageX, levelMessageY;

    public MinerMinionContainerScreen(MinionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        levelMessage = new TranslatableComponent("skyblock.gui.minion.level", pMenu.getMinerEntity().getMinionLevel()).withStyle(ChatFormatting.BLACK);
        imageHeight = 168;
        this.levelUpButton = new MinionLevelUpButton(pMenu.getMinerEntity(), pPlayerInventory, this);
        this.collectAllButton = new MinionCollectAllButton(pMenu, this);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(levelUpButton);
        addRenderableWidget(collectAllButton);
        titleLabelX = font.width(title) / 2;
        titleLabelY = 5;
        levelMessageX = getGuiLeft() + font.width(title) / 2 + font.width(title);
        levelMessageY = getGuiTop() + titleLabelY;
        inventoryLabelX = imageWidth - font.width(playerInventoryTitle) - 7;
        inventoryLabelY = imageHeight - 95;
        this.levelUpButton.x = leftPos + imageWidth - levelUpButton.getWidth() - 16;
        this.levelUpButton.y = topPos + 20;
        this.collectAllButton.x = levelUpButton.x;
        this.collectAllButton.y = levelUpButton.y + 30;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        font.draw(pPoseStack, levelMessage, levelMessageX, levelMessageY, Color.WHITE.getRGB());
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, background);

        int x = (width - imageWidth) / 2;
        int y = (height - imageWidth) / 2;
        blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderSlotLocks(pPoseStack, pMouseX, pMouseY);
    }


    private void renderSlotLocks(PoseStack poseStack, int mouseX, int mouseY) {
        if (minionLevel < 2) {
            renderSlotLock(poseStack, mouseX, mouseY, 2, 1);
            renderSlotLock(poseStack, mouseX, mouseY, 2, 2);
        }

        if (minionLevel < 4) {
            renderSlotLock(poseStack, mouseX, mouseY, 4, 3);
            renderSlotLock(poseStack, mouseX, mouseY, 4, 4);
            renderSlotLock(poseStack, mouseX, mouseY, 4, 5);
        }

        if (minionLevel < 6) {
            renderSlotLock(poseStack, mouseX, mouseY, 6, 6);
            renderSlotLock(poseStack, mouseX, mouseY, 6, 7);
            renderSlotLock(poseStack, mouseX, mouseY, 6, 8);
        }

        if (minionLevel < 8) {
            renderSlotLock(poseStack, mouseX, mouseY, 8, 9);
            renderSlotLock(poseStack, mouseX, mouseY, 8, 10);
            renderSlotLock(poseStack, mouseX, mouseY, 8, 11);
        }

        if (minionLevel < 10) {
            renderSlotLock(poseStack, mouseX, mouseY, 10, 12);
            renderSlotLock(poseStack, mouseX, mouseY, 10, 13);
            renderSlotLock(poseStack, mouseX, mouseY, 10, 14);
        }
    }

    private void renderSlotLock(PoseStack poseStack, int mouseX, int mouseY, int unlockAt, int slotNumber) {
        int x = (slotNumber % 5) * 18 + getGuiLeft() + 44;
        int y = (slotNumber / 5) * 18 + getGuiTop() + 20;
        Color slotLockColor = new Color(255, 0, 0, 128);

        GuiUtils.drawGradientRect(poseStack.last().pose(), 0, x, y, x + 16, y + 16, slotLockColor.getRGB(), slotLockColor.getRGB());

        drawCenteredString(poseStack, font, String.valueOf(unlockAt), x + 8, y + 4, Color.GRAY.getRGB());

        if (mouseX >= x && mouseX <= x + 16) {
            if (mouseY >= y && mouseY <= y + 16) {
                MutableComponent hoverMessage = new TranslatableComponent("skyblock.gui.minion.slot.unlock.hover", new TextComponent(String.valueOf(unlockAt))
                    .withStyle(ChatFormatting.BOLD)
                    .withStyle(ChatFormatting.DARK_RED))
                    .withStyle(ChatFormatting.RED);

                renderTooltip(poseStack, font.split(hoverMessage, 150), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (minionLevel != getMenu().getMinerEntity().getMinionLevel()) {
            minionLevel = getMenu().getMinerEntity().getMinionLevel();
            levelMessage = new TranslatableComponent("skyblock.gui.minion.level", getMenu().getMinerEntity().getMinionLevel()).withStyle(ChatFormatting.BLACK);
        }
    }
}
