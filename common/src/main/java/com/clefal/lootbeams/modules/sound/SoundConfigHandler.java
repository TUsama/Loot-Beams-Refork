package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import com.google.common.base.Supplier;
import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.stream.Stream;
@UtilityClass
public class SoundConfigHandler {

    public static boolean checkInBlackList(LBItemEntity lbItemEntity){
        SoundConfig.SoundFilter soundFilter = SoundConfig.soundConfig.soundFilter;

        Supplier<Boolean> b = () -> Checker.checkItemInItemList(lbItemEntity, soundFilter.blacklist_by_name);
        Supplier<Boolean> b1 = () -> Checker.checkItemHasTagInTagList(lbItemEntity, soundFilter.blacklist_by_tag);
        Supplier<Boolean> b2 = () -> Checker.checkIsInThisModList(lbItemEntity, soundFilter.blacklist_by_modid);
        return Stream.of(b, b1, b2).anyMatch(Supplier::get);
    }

}
