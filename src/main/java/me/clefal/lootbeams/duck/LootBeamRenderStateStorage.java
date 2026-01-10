package me.clefal.lootbeams.duck;

import me.clefal.lootbeams.data.new_render.LootBeamRenderState;

import java.util.List;


public interface LootBeamRenderStateStorage {

    void loot_Beams_Refork$submitBeam(LootBeamRenderState.BeamRenderState renderState);
    void loot_Beams_Refork$submitNameTag(LootBeamRenderState.NameTagRenderState renderState);
    List<LootBeamRenderState.BeamRenderState> loot_Beams_Refork$getBeamRenderStates();
    List<LootBeamRenderState.NameTagRenderState> loot_Beams_Refork$getNameTagRenderStates();
}
