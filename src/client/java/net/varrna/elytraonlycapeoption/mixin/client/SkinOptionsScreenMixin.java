package net.varrna.elytraonlycapeoption.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.text.Text;
import net.varrna.elytraonlycapeoption.CapeOption;
import net.varrna.elytraonlycapeoption.ElytraOnlyCapeOptionClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends GameOptionsScreen {


    public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "addOptions", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD,  cancellable = true)
    public void addCapeOption(CallbackInfo ci) {
        List<ClickableWidget> list = new ArrayList<>();
        list.add(
                CyclingButtonWidget.builder(CapeOption::getDisplayName)
                        .values(CapeOption.values())
                        .initially(ElytraOnlyCapeOptionClient.getCapeOption())
                        .build(Text.translatable("options.cape"), (button, option) -> {
                            ElytraOnlyCapeOptionClient.setCapeOption(option);
                        })
        );
        for (PlayerModelPart playerModelPart : PlayerModelPart.values()) {
            if(!playerModelPart.getName().equals("cape")){
                list.add(
                        CyclingButtonWidget.onOffBuilder(this.gameOptions.isPlayerModelPartEnabled(playerModelPart))
                                .build(playerModelPart.getOptionName(), (cyclingButtonWidget, boolean_) -> this.gameOptions.togglePlayerModelPart(playerModelPart, boolean_))
                );
            }

        }

        list.add(this.gameOptions.getMainArm().createWidget(this.gameOptions));
        assert this.body != null;
        this.body.addAll(list);
        ci.cancel();
    }
}
