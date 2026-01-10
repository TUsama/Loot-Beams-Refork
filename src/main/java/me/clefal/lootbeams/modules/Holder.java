package me.clefal.lootbeams.modules;

import net.minecraft.client.renderer.MultiBufferSource;
//? >=1.21.10
//import net.minecraft.client.renderer.SubmitNodeCollector;

public class Holder {
    private MultiBufferSource bufferSource;
    //? 1.21.10
    //private SubmitNodeCollector collector;

    //? 1.21.10 {
    /*public Holder(SubmitNodeCollector collector) {
        this.collector = collector;
    }
    *///?}
    public Holder(MultiBufferSource bufferSource) {
        this.bufferSource = bufferSource;
    }

    //? <1.21.10 {
    public MultiBufferSource get(){
        return this.bufferSource;
    }
    //? } else {
    /*public SubmitNodeCollector get(){
        return this.collector;
    }
    *///? }
}
