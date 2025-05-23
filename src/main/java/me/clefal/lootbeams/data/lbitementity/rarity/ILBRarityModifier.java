package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.data.lbitementity.LBItemEntity;

public interface ILBRarityModifier {

    LBItemEntity modify(LBItemEntity lbItemEntity);
}
