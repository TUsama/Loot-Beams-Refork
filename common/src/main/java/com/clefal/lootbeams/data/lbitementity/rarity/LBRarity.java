package com.clefal.lootbeams.data.lbitementity.rarity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.awt.*;

public record LBRarity(Component name, Color color, int absoluteOrdinal) {
    public final static String vanillaRarityKeFormat = "lootbeams.vanilla_rarity.";

    public static LBRarity of(Component name, Color color, int absoluteOrdinal) {
        return new LBRarity(name, color, absoluteOrdinal);
    }

    public static LBRarity of(Rarity rarity){
        Component translatable = Component.translatable(vanillaRarityKeFormat + rarity.name().toLowerCase());
        return new LBRarity(translatable, new Color(rarity.color().getColor()), rarity.ordinal());
    }
}
