//? >= 1.21.10 {
/*package me.clefal.lootbeams.mixin.refactor;

import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.LootBeamRenderStateStorage;
import net.minecraft.client.renderer.SubmitNodeCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin implements LootBeamRenderStateStorage {
    @Shadow
    private boolean wasUsed;
    private List<LootBeamRenderState.BeamRenderState> beams = new ArrayList<>();
    private List<LootBeamRenderState.NameTagRenderState> nameTagRenderStates = new ArrayList<>();

    @Inject(
            method = "clear", at = @At(
            value = "RETURN"
    ))
    private void clearCache(CallbackInfo ci){
        beams.clear();
        nameTagRenderStates.clear();
    }


    @Override
    public void loot_Beams_Refork$submitBeam(LootBeamRenderState.BeamRenderState renderState) {
        this.wasUsed = true;
        this.beams.add(renderState);
    }

    @Override
    public void loot_Beams_Refork$submitNameTag(LootBeamRenderState.NameTagRenderState renderState) {
        this.wasUsed = true;
        this.nameTagRenderStates.add(renderState);
    }

    @Override
    public List<LootBeamRenderState.BeamRenderState> loot_Beams_Refork$getBeamRenderStates() {
        return beams;
    }

    @Override
    public List<LootBeamRenderState.NameTagRenderState> loot_Beams_Refork$getNameTagRenderStates() {
        return nameTagRenderStates;
    }
}
*///? }