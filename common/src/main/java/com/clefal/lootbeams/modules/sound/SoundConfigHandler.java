package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import com.google.common.base.Supplier;
import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;

import java.util.stream.Stream;
@UtilityClass
public class SoundConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        SoundConfig.SoundFilter soundFilter = SoundConfig.soundConfig.soundFilter;
        Supplier<Boolean> b = () -> soundFilter.blacklist_by_name.stream()
                .anyMatch(x -> Checker.checkItemEquality(lbItemEntity, x));
        Supplier<Boolean> b1 = () -> soundFilter.blacklist_by_tag.stream()
                .map(x -> x.replace("#", ""))
                .anyMatch(x -> Checker.checkTagContainItem(lbItemEntity, ResourceLocationHelper.fromWholeName(x)));
        Supplier<Boolean> b2 = () -> soundFilter.blacklist_by_modid.stream()
                .anyMatch(x -> Checker.checkIsThisMod(lbItemEntity, x));
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);
    }

}
