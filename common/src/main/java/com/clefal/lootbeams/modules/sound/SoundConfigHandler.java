package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.google.common.base.Supplier;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;
@UtilityClass
public class SoundConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        SoundConfig.SoundFilter soundFilter = SoundConfig.soundConfig.soundFilter;

        Supplier<Boolean> b = () -> Checker.checkItemInItemSet(lbItemEntity, soundFilter.blacklist_by_name);
        Supplier<Boolean> b1 = () -> Checker.checkItemHasTagInTagSet(lbItemEntity, soundFilter.blacklist_by_tag);
        Supplier<Boolean> b2 = () -> Checker.checkIsInThisModSet(lbItemEntity, soundFilter.blacklist_by_modid);
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);
    }

}
