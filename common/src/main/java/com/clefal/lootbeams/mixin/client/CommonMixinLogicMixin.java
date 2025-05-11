package com.clefal.lootbeams.mixin.client;

import com.clefal.lootbeams.modules.compat.photon.PhotonCompatConfig;
import com.clefal.lootbeams.modules.compat.subtle_effect.SubtleEffectCompatModule;
import einstein.subtle_effects.util.CommonMixinLogic;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CommonMixinLogic.class, remap = false)
public class CommonMixinLogicMixin {
    @Inject(method = "shouldRenderParticle", at = @At(
            value = "HEAD"
    ), cancellable = true)
    private static void onEnableFX(Particle particle, Camera camera, CallbackInfoReturnable<Boolean> cir){
        if (SubtleEffectCompatModule.INSTANCE.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableParticleCull) {
            if (PhotonCompatConfig.getConfig().fxEnable.enableFX) {
                cir.setReturnValue(true);
            }
        }
    }
    
}
