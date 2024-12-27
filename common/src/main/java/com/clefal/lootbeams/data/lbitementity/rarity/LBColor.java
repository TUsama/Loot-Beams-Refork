package com.clefal.lootbeams.data.lbitementity.rarity;

import net.minecraft.network.chat.TextColor;

public class LBColor {

    int argb;

    public LBColor(int argb) {
        this.argb = argb;
    }

    public static LBColor of(int argb) {
        return new LBColor(argb);
    }

    public static LBColor.Mutable ofMutable(TextColor color) {

        return new Mutable((color));
    }


    public int rgb() {
        return argb & 0xFFFFFF;
    }

    public LBColor changeA(int a){
        return new LBColor((a << 24) | (argb() & 0x00FFFFFF));
    }


    public int argb() {
        return argb;
    }

    public static class Mutable extends LBColor{
        TextColor color;

        private Mutable(TextColor color) {
            super(color.getValue());
            this.color = color;
        }

        @Override
        public int rgb() {
            return this.color.getValue();
        }

        @Override
        public int argb() {
            int a = (argb >> 24) & 0xFF;
            return (a << 24) | rgb();
        }
    }

}
