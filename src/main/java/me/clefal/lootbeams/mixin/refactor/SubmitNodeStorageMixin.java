//? >= 1.21.10 {
/*package me.clefal.lootbeams.mixin.refactor;

import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.LootBeamRenderStateStorage;
import me.clefal.lootbeams.duck.LootBeamRenderStateSubmitter;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SubmitNodeStorage.class)
public abstract class SubmitNodeStorageMixin implements LootBeamRenderStateSubmitter {

    @Shadow
    public abstract SubmitNodeCollection order(int p_439752_);

    @Override
    public void loot_Beams_Refork$submitBeam(LootBeamRenderState.BeamRenderState renderState) {
        ((LootBeamRenderStateStorage) this.order(0)).loot_Beams_Refork$submitBeam(renderState);
    }

    @Override
    public void loot_Beams_Refork$submitNameTag(LootBeamRenderState.NameTagRenderState renderState) {
        ((LootBeamRenderStateStorage) this.order(0)).loot_Beams_Refork$submitNameTag(renderState);
    }
}
*///? }