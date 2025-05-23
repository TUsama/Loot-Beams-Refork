package me.clefal.lootbeams.data.lbitementity.rarity;

public record ModifyContext(boolean configColorOverride) {
    public boolean hasBeenModified(){
        return configColorOverride;
    }
}
