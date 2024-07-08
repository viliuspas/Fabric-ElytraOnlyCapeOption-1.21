package net.varrna.elytraonlycapeoption;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElytraOnlyCapeOption implements ModInitializer {
	public static final String MOD_ID = "elytra-only-cape-option";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
	}
}