package net.varrna.elytraonlycapeoption;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ElytraOnlyCapeOptionClient implements ClientModInitializer {
	private static final Path CONFIG_PATH = Paths.get("config", "elytraonlycapeoption.txt");
	private static CapeOption capeOption = CapeOption.ELYTRA_ONLY;
	@Override
	public void onInitializeClient() {
		loadCapeOption();

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
		saveCapeOption();
	}

	public static CapeOption getCapeOption() {
		return capeOption;
	}

	private static void saveCapeOption() {
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			Files.writeString(CONFIG_PATH, capeOption.name());
		} catch (IOException ignore) {}
	}

	private static void loadCapeOption() {
		if (Files.exists(CONFIG_PATH)) {
			try {
				String optionName = Files.readString(CONFIG_PATH).trim();
				capeOption = CapeOption.valueOf(optionName);
			} catch (IOException | IllegalArgumentException ignore) {}
		}
	}
}