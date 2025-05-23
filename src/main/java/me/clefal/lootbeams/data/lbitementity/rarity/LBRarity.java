package me.clefal.lootbeams.data.lbitementity.rarity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;

import java.awt.*;

public record LBRarity(Component name, LBColor color, int absoluteOrdinal, ModifyContext context) {
    public final static String vanillaRarityKeFormat = "lootbeams.vanilla_rarity.";

    public static LBRarity of(Component name, LBColor color, int absoluteOrdinal) {
        return new LBRarity(name, color, absoluteOrdinal, new ModifyContext(false));
    }

    public static LBRarity ofVanillaRarity(Rarity rarity){
        Component translatable = Component.translatable(vanillaRarityKeFormat + rarity.name().toLowerCase());
        return new LBRarity(translatable, LBColor.of(TextColor.fromLegacyFormat(rarity.color()).getValue()), rarity.ordinal(), new ModifyContext(false));
    }

    public LBRarity configModifyColor(LBColor color){
        return new LBRarity(name, color, absoluteOrdinal, new ModifyContext(true));
    }


}
