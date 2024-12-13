package com.clefal.lootbeams.config.services;

import com.clefal.lootbeams.data.LBItemEntity;

public interface IServicesChecker {

    boolean checkItemEquality(LBItemEntity lbItemEntity, String resourceLocation);

    boolean checkTagContainItem(LBItemEntity lbItemEntity, String tagWithoutNumberSign);

    boolean checkIsThisMod(LBItemEntity lbItemEntity, String modId);
}
