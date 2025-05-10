package com.clefal.lootbeams.modules.dynamicprovider;

import com.clefal.lootbeams.config.configs.DynamicConfig;
import com.clefal.lootbeams.events.LBClientTickEvent;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import lombok.Setter;

public class DynamicProvider {
    @Setter
    private int halfRoundTicks;
    private int currentTicks;
    private float alterFactor;
    private boolean shouldDecrease;

    public DynamicProvider() {
        this.halfRoundTicks = DynamicConfig.dynamicConfig.half_round_ticks.get();
        this.alterFactor = 0.0f;
        this.shouldDecrease = false;
    }

    public float getBeamLightFactor(){
        return 0.5f * alterFactor + 0.6f;
    }


    public float getGlowFactor(){
        return alterFactor + 0.1f;
    }


    @SubscribeEvent
    public void updateProvider(LBClientTickEvent event){
        currentTicks++;
        if (!shouldDecrease){
            alterFactor = 1.0f * currentTicks / halfRoundTicks;
        } else {
            alterFactor = 1.0f * (halfRoundTicks -  currentTicks) / halfRoundTicks;
        }
        if (currentTicks >= halfRoundTicks) {
            shouldDecrease = !shouldDecrease;
            currentTicks = 0;
        }
    }
}
