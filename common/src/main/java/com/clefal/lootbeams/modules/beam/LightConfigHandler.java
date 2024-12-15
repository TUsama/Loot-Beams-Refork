package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;

public class LightConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter soundFilter = LightConfig.lightConfig.lightEffectFilter;
        return soundFilter.blacklist.stream()
                .anyMatch(x -> {
                    IServicesChecker checker = PlatformChecker.PLATFORM;
                    return checker.checkItemEquality(lbItemEntity, x) ||  checker.checkTagContainItem(lbItemEntity, x) || checker.checkIsThisMod(lbItemEntity, x);
                });
    }

    public static boolean checkInWhiteList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter soundFilter = LightConfig.lightConfig.lightEffectFilter;
        return soundFilter.whitelist.stream()
                .anyMatch(x -> {
                    IServicesChecker checker = PlatformChecker.PLATFORM;
                    return checker.checkItemEquality(lbItemEntity, x) ||  checker.checkTagContainItem(lbItemEntity, x) || checker.checkIsThisMod(lbItemEntity, x);
                });
    }
}
