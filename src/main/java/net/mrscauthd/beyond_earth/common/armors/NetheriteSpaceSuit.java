package net.mrscauthd.beyond_earth.common.armors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.client.renderers.armors.SpaceSuitModel;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class NetheriteSpaceSuit {

	public static class OxygenMask extends ISpaceArmor.Helmet {
		public OxygenMask(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of("head", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).head,

							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = livingEntity;
					armorModel.itemStack = itemStack;

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/netherite_oxygen_mask.png";
		}
	}

	public static class Suit extends ISpaceArmor.Chestplate {

		public Suit(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"body", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).body,
							"right_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).rightArm,
							"left_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).leftArm,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = livingEntity;
					armorModel.itemStack = itemStack;

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/netherite_space_suit.png";
		}

		@Override
		public int getOxygenCapacity() {
			return 48000;
		}
	}

	public static class Pants extends ISpaceArmor.Leggings {
		public Pants(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P2 armorModel = new SpaceSuitModel.SPACE_SUIT_P2(modelPart);

					armorModel.entity = livingEntity;
					armorModel.itemStack = itemStack;

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/netherite_space_pants.png";
		}
	}

	public static class Boots extends ISpaceArmor.Boots {
		public Boots(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = livingEntity;
					armorModel.itemStack = itemStack;

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/netherite_space_suit.png";
		}
	}
}