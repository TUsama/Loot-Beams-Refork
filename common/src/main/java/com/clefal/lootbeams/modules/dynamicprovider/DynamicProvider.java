package com.clefal.lootbeams.modules.dynamicprovider;

import com.clefal.lootbeams.events.LBClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class DynamicProvider {

    private final int halfRoundTicks;
    private int currentTicks;
    private float alterFactor;
    private boolean shouldDecrease;

    public DynamicProvider(int halfRoundTicks) {
        this.halfRoundTicks = halfRoundTicks;
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
