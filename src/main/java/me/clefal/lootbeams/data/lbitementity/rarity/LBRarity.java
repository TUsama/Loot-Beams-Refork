package me.clefal.lootbeams.data.lbitementity.rarity;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;

public record LBRarity(Component name, LBColor color, int absoluteOrdinal, ModifyContext context) {
    public final static String vanillaRarityKeFormat = "lootbeams.vanilla_rarity.";

    public static LBRarity of(Component name, LBColor color, int absoluteOrdinal) {
        return new LBRarity(name, color, absoluteOrdinal, new ModifyContext(false));
    }

    public static LBRarity ofVanillaRarity(Rarity rarity){
        Component name;
        if (I18n.exists(vanillaRarityKeFormat + rarity.name().toLowerCase())) {
            name = Component.translatable(vanillaRarityKeFormat + rarity.name().toLowerCase());
        } else {
            name = Component.literal(rarity.name().toLowerCase());
        }
        return new LBRarity(name, LBColor.of(
                //this is so horrible
                //? !fabric {
                rarity.getStyleModifier().apply(Style.EMPTY).getColor().getValue()
                //?} else {
                /*grabColorWhenOnStupidFabric(rarity)
                *///?}

        ), rarity.ordinal(), new ModifyContext(false));
    }

    public LBRarity modifyColor(LBColor color){
        return new LBRarity(name, color, absoluteOrdinal, new ModifyContext(true));
    }

    //? fabric {
    /*private static int grabColorWhenOnStupidFabric(Rarity rarity){
        //? 1.20.1 {
        return rarity.color.getColor();
        //?} else {
        /^return rarity.color().getColor();
        ^///?}
    }
    *///?}
}