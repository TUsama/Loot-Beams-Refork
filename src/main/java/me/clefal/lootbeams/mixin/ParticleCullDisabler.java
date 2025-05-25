//? 1.20.1 {
/*package me.clefal.lootbeams.mixin;

/^
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
^/
import einstein.subtle_effects.util.CommonMixinLogic;
import me.clefal.lootbeams.compat.common_1_20_1.SubtleEffectCompatModule;
import me.clefal.lootbeams.compat.common_1_20_1.photon.PhotonCompatConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;



import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CommonMixinLogic.class, remap = false)
public class ParticleCullDisabler {

    @Inject(method = "shouldRenderParticle", at = @At(
            value = "HEAD"
    ), cancellable = true)
    private static void onEnableFX(Particle particle, Camera camera, CallbackInfoReturnable<Boolean> cir){
        if (SubtleEffectCompatModule.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableParticleCull) {
            if (PhotonCompatConfig.getConfig().fxEnable.enableFX) {
                cir.setReturnValue(true);
            }
        }
    }



    /^
    @ModifyExpressionValue(method = "shouldRenderParticle", at = @At(value = "FIELD", target = "Leinstein/subtle_effects/configs/ModGeneralConfigs;enableParticleCulling:Z"))
    private static boolean onEnableFX1(boolean original) {
        boolean b = SubtleEffectCompatModule.INSTANCE.isEnabled && SubtleEffectCompatModule.getConfig().forceDisableParticleCull && PhotonCompatConfig.getConfig().fxEnable.enableFX;
        return !b;
    }^/

}
*///?}



