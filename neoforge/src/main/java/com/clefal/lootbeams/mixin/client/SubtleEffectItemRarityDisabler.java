package com.clefal.lootbeams.mixin.client;

import com.clefal.lootbeams.compat.SubtleEffectCompatModule;
import einstein.subtle_effects.tickers.ItemRarityTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRarityTicker.class, remap = false)
public class SubtleEffectItemRarityDisabler {

    @Inject(method = "tick",
    at = @At(value = "HEAD"), cancellable = true)
    public void onDisable(CallbackInfo ci){
        if (SubtleEffectCompatModule.isEnabled && SubtleEffectCompatModule.getConfig().disableItemRarityParticleRender) ci.cancel();
    }
}
