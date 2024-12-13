package com.clefal.lootbeams.data.rarity;

import com.clefal.lootbeams.data.LBItemEntity;

public interface ILBRarityModifier {

    LBItemEntity modify(LBItemEntity lbItemEntity);
}
