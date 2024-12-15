package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.resources.ResourceLocation;

public class SoundConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        SoundConfig.SoundFilter soundFilter = SoundConfig.soundConfig.soundFilter;
        return soundFilter.blacklist.stream()
                .anyMatch(x -> {
                    IServicesChecker checker = PlatformChecker.PLATFORM;
                    return checker.checkItemEquality(lbItemEntity, x) ||  checker.checkTagContainItem(lbItemEntity, x) || checker.checkIsThisMod(lbItemEntity, x);
                });
    }

    public static boolean checkInWhiteList(LBItemEntity lbItemEntity){
        SoundConfig.SoundFilter soundFilter = SoundConfig.soundConfig.soundFilter;
        return soundFilter.whitelist.stream()
                .anyMatch(x -> {
                    IServicesChecker checker = PlatformChecker.PLATFORM;
                    return checker.checkItemEquality(lbItemEntity, x) ||  checker.checkTagContainItem(lbItemEntity, x) || checker.checkIsThisMod(lbItemEntity, x);
                });
    }
}
