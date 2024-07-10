package net.varrna.elytraonlycapeoption;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;

public class ElytraOnlyCapeOptionClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (client.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
					// Enable cape
					enableCape(client, true);
				} else {
					// Disable cape
					enableCape(client, false);
				}
			}
		});
	}

	private void enableCape(MinecraftClient client, boolean enable) {
		if (client.player != null) {
			client.options.togglePlayerModelPart(PlayerModelPart.CAPE, enable);
		}
	}
}