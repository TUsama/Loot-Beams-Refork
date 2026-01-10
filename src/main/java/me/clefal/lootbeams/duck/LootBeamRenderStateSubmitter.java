package me.clefal.lootbeams.duck;

import me.clefal.lootbeams.data.new_render.LootBeamRenderState;


public interface LootBeamRenderStateSubmitter {

    void loot_Beams_Refork$submitBeam(LootBeamRenderState.BeamRenderState renderState);

    void loot_Beams_Refork$submitNameTag(LootBeamRenderState.NameTagRenderState renderState);
}
