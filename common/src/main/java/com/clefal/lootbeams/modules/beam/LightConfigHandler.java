package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.google.common.base.Supplier;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Stream;

public class LightConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter lightEffectFilter = LightConfig.lightConfig.lightEffectFilter;

        Supplier<Boolean> b = () -> Checker.checkItemInItemList(lbItemEntity, lightEffectFilter.blacklist_by_name);
        Supplier<Boolean> b1 = () -> Checker.checkItemHasTagInTagList(lbItemEntity, lightEffectFilter.blacklist_by_tag);
        Supplier<Boolean> b2 = () -> Checker.checkIsInThisModList(lbItemEntity, lightEffectFilter.blacklist_by_modid);
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);
    }

    public static boolean checkInWhiteList(LBItemEntity lbItemEntity){
        LightConfig.LightEffectFilter lightEffectFilter = LightConfig.lightConfig.lightEffectFilter;


        Supplier<Boolean> b = () -> Checker.checkItemInItemList(lbItemEntity, lightEffectFilter.whitelist_by_name);
        Supplier<Boolean> b1 = () -> Checker.checkItemHasTagInTagList(lbItemEntity, lightEffectFilter.whitelist_by_tag);
        Supplier<Boolean> b2 = () -> Checker.checkIsInThisModList(lbItemEntity, lightEffectFilter.whitelist_by_modid);
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);

    }

}
