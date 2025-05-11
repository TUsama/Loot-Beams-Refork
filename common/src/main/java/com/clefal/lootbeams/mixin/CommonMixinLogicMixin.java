package com.clefal.lootbeams.mixin;

import com.clefal.lootbeams.modules.compat.photon.PhotonCompatConfig;
import com.clefal.lootbeams.modules.compat.subtle_effect.SubtleEffectCompatModule;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import einstein.subtle_effects.util.CommonMixinLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CommonMixinLogic.class, remap = false)
public class CommonMixinLogicMixin {
    /*@Inject(method = "shouldRenderParticle", at = @At(
            value = "HEAD"
    ), cancellable = true)
    private static void onEnableFX(Particle particle, Camera camera, CallbackInfoReturnable<Boolean> cir){
        if (SubtleEffectCompatModule.INSTANCE.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableParticleCull) {
            if (PhotonCompatConfig.getConfig().fxEnable.enableFX) {
                cir.setReturnValue(true);
            }
        }
    }*/



    @ModifyExpressionValue(method = "shouldRenderParticle", at = @At(value = "FIELD", target = "Leinstein/subtle_effects/configs/ModGeneralConfigs;enableParticleCulling:Z"))
    private static boolean onEnableFX1(boolean original) {
        boolean b = SubtleEffectCompatModule.INSTANCE.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableParticleCull && PhotonCompatConfig.getConfig().fxEnable.enableFX;
        return !b;
    }
}
