package com.clefal.lootbeams.data.rarity;

import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.LBItemEntity;
import com.mojang.datafixers.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.function.BiFunction;

public enum Order {
    ITEM((list, itemWithRarity) -> {
        for (Pair<String, Color> stringColorPair : list) {
            if (PlatformChecker.PLATFORM.checkItemEquality(itemWithRarity, stringColorPair.getFirst())) {
                return itemWithRarity.to(LBRarity.of(itemWithRarity.rarity().name(), stringColorPair.getSecond(), itemWithRarity.rarity().absoluteOrdinal()));
            }
        }
        return itemWithRarity;
    }),
    TAG((list, itemWithRarity) -> {
        for (Pair<String, Color> stringColorPair : list) {
            if (PlatformChecker.PLATFORM.checkTagContainItem(itemWithRarity, stringColorPair.getFirst())) {
                return itemWithRarity.to(LBRarity.of(itemWithRarity.rarity().name(), stringColorPair.getSecond(), itemWithRarity.rarity().absoluteOrdinal()));
            }
        }
        return itemWithRarity;
    }),
    MODID((list, itemWithRarity) -> {
        for (Pair<String, Color> stringColorPair : list) {
            if (PlatformChecker.PLATFORM.checkIsThisMod(itemWithRarity, stringColorPair.getFirst())) {
                return itemWithRarity.to(LBRarity.of(itemWithRarity.rarity().name(), stringColorPair.getSecond(), itemWithRarity.rarity().absoluteOrdinal()));
            }
        }
        return itemWithRarity;
    });

    public final BiFunction<List<Pair<String, Color>>, LBItemEntity, LBItemEntity> mutate;

    Order(BiFunction<List<Pair<String, Color>>, LBItemEntity, LBItemEntity> mutate) {
        this.mutate = mutate;
    }
}
