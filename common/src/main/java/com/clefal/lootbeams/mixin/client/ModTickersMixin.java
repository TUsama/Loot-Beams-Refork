package com.clefal.lootbeams.mixin.client;

import com.clefal.lootbeams.modules.compat.photon.PhotonCompatModule;
import com.clefal.lootbeams.modules.compat.subtle_effect.SubtleEffectCompatModule;
import einstein.subtle_effects.tickers.ItemRarityTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRarityTicker.class)
public class ModTickersMixin {

    @Inject(
            method = "tick", at = @At(
            value = "HEAD"
    ), remap = false, cancellable = true)
    public void onTick(CallbackInfo ci) {
        if (SubtleEffectCompatModule.INSTANCE.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableItemRarity) {
            if (PhotonCompatModule.getConfig().fxEnable.enableFX) {
                ci.cancel();
            }
        }
    }

}
