package com.clefal.lootbeams.config.services;

import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.resources.ResourceLocation;

public interface IServicesChecker {

    boolean checkItemEquality(LBItemEntity lbItemEntity, ResourceLocation resourceLocation);

    boolean checkTagContainItem(LBItemEntity lbItemEntity, ResourceLocation tagWithoutNumberSign);

    boolean checkIsThisMod(LBItemEntity lbItemEntity, ResourceLocation modId);
}
