package net.mrscauthd.beyond_earth.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.SolarPanelBlockEntity;
import net.mrscauthd.beyond_earth.common.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.common.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.common.menus.SolarPanelMenu;

@OnlyIn(Dist.CLIENT)
public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelMenu.GuiContainer> {
	public static final ResourceLocation texture = new ResourceLocation(BeyondEarth.MODID, "textures/gui/solar_panel.png");

	public SolarPanelScreen(SolarPanelMenu.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 177;
		this.imageHeight = 228;
		this.inventoryLabelY = this.imageHeight - 92;
		this.titleLabelY += 40;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		super.renderLabels(ms, p_97809_, p_97810_);

		SolarPanelBlockEntity blockEntity = this.getMenu().getBlockEntity();
		IEnergyStorage energyStorage = blockEntity.getPrimaryEnergyStorage();

		this.font.draw(ms, GaugeTextHelper.getStoredText(GaugeValueHelper.getEnergy(energyStorage.getEnergyStored())).build(), this.titleLabelY, 28, 0x3C3C3C);
		this.font.draw(ms, GaugeTextHelper.getCapacityText(GaugeValueHelper.getEnergy(energyStorage.getMaxEnergyStored())).build(), this.titleLabelY, 40, 0x3C3C3C);
		this.font.draw(ms, GaugeTextHelper.getMaxGenerationPerTickText(GaugeValueHelper.getEnergy(blockEntity.getMaxGeneration())).build(), this.titleLabelY, 52, 0x3C3C3C);
	}
}
