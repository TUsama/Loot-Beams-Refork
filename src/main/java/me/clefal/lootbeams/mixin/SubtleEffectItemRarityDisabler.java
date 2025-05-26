package me.clefal.lootbeams.mixin;

import einstein.subtle_effects.tickers.ItemRarityTicker;
//? if 1.20.1 {
/*import me.clefal.lootbeams.compat.common_1_20_1.SubtleEffectCompatModule;

*///?} else if 1.21.1 {
/*import me.clefal.lootbeams.compat.common_1_21_1.SubtleEffectCompatModule;
*///?} else if 1.21.4 {
import me.clefal.lootbeams.compat.common_1_21_4.SubtleEffectCompatModule;
//?}

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRarityTicker.class, remap = false)
public class SubtleEffectItemRarityDisabler {

    @Inject(method = "tick",
    at = @At(value = "HEAD"), cancellable = true)
    public void onDisable(CallbackInfo ci){
        if (SubtleEffectCompatModule.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableItemRarity) ci.cancel();
    }
}
