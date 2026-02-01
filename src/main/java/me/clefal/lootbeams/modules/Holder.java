package me.clefal.lootbeams.modules;

import net.minecraft.client.renderer.MultiBufferSource;
//? >=1.21.10
//import net.minecraft.client.renderer.SubmitNodeCollector;

public class Holder {

    //? >=1.21.10 {
    /*private SubmitNodeCollector collector;

    public Holder(SubmitNodeCollector collector) {
        this.collector = collector;
    }

    public SubmitNodeCollector get(){
        return this.collector;
    }
    *///? } else {
    private MultiBufferSource bufferSource;
    public Holder(MultiBufferSource bufferSource) {
        this.bufferSource = bufferSource;
    }
    public MultiBufferSource get(){
        return this.bufferSource;
    }
    //?}

}
