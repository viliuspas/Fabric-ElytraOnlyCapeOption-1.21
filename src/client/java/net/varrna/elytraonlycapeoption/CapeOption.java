package net.varrna.elytraonlycapeoption;

import net.minecraft.text.Text;

public enum CapeOption {
    ON("options.cape.on"),
    OFF("options.cape.off"),
    ELYTRA_ONLY("options.cape.elytra_only");

    private final String translationKey;

    CapeOption(String translationKey) {
        this.translationKey = translationKey;
    }

    public Text getDisplayName() {
        return Text.translatable(this.translationKey);
    }
}
