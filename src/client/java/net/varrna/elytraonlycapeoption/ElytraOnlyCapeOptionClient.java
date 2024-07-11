package net.varrna.elytraonlycapeoption;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;
import net.minecraft.network.message.ChatVisibility;

import java.util.Arrays;

public class ElytraOnlyCapeOptionClient implements ClientModInitializer {
	private static CapeOption capeOption;
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				switch (capeOption) {
					case ON:
						enableCape(client, true);
						break;
					case OFF:
						enableCape(client, false);
						break;
					case ELYTRA_ONLY:
						if (client.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
							enableCape(client, true);
						} else {
							enableCape(client, false);
						}
						break;
				}
			}
		});
	}

	private void enableCape(MinecraftClient client, boolean enable) {
		if (client.player != null) {
			client.options.togglePlayerModelPart(PlayerModelPart.CAPE, enable);
		}
	}

	public static void setCapeOption(CapeOption option) {
		capeOption = option;
	}

	public static CapeOption getCapeOption() {
		return capeOption;
	}
}