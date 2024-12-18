package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.utils.CheckedRunnable;
import com.google.common.base.Supplier;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.stream.Stream;

public class LightConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter lightEffectFilter = LightConfig.lightConfig.lightEffectFilter;
        Supplier<Boolean> b = () -> lightEffectFilter.blacklist_by_name.stream()
                .anyMatch(x -> Checker.checkItemEquality(lbItemEntity, x));
        Supplier<Boolean> b1 = () -> lightEffectFilter.blacklist_by_tag.stream()
                .map(x -> x.replace("#", ""))
                .anyMatch(x -> Checker.checkTagContainItem(lbItemEntity, ResourceLocation.bySeparator(x, ':')));
        Supplier<Boolean> b2 = () -> lightEffectFilter.blacklist_by_modid.stream()
                .anyMatch(x -> Checker.checkIsThisMod(lbItemEntity, x));
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);
    }

    public static boolean checkInWhiteList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter lightEffectFilter = LightConfig.lightConfig.lightEffectFilter;
        Supplier<Boolean> b = () -> lightEffectFilter.whitelist_by_name.stream()
                .anyMatch(x -> Checker.checkItemEquality(lbItemEntity, x));
        Supplier<Boolean> b1 = () -> lightEffectFilter.whitelist_by_tag.stream()
                .map(x -> x.replace("#", ""))
                .anyMatch(x -> Checker.checkTagContainItem(lbItemEntity, ResourceLocation.bySeparator(x, ':')));
        Supplier<Boolean> b2 = () -> lightEffectFilter.whitelist_by_modid.stream()
                .anyMatch(x -> Checker.checkIsThisMod(lbItemEntity, x));
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);

    }

}
