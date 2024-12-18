package com.clefal.lootbeams.data.lbitementity.rarity;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ConfigColorOverride extends ModifyingConfigHandler {


    public ConfigColorOverride() {

    }


    @Override
    public LBItemEntity modify(LBItemEntity lbItemEntity) {
        LightConfig.CustomColorSetting customColorSetting = LightConfig.lightConfig.customColorSetting;
        if (!customColorSetting.enable_custom_color) return lbItemEntity;
        AtomicReference<LBItemEntity> result = new AtomicReference<>(lbItemEntity);
        Supplier<Optional<ValidatedColor.ColorHolder>> o = () -> customColorSetting.color_override_by_name
                .entrySet()
                .stream()
                .filter(x -> Checker.checkItemEquality(lbItemEntity, x.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

        Supplier<Optional<ValidatedColor.ColorHolder>> o1 = () -> customColorSetting.color_override_by_tag
                .entrySet()
                .stream()
                .filter(x -> Checker.checkTagContainItem(lbItemEntity, ResourceLocation.bySeparator(x.getKey().replace("#", ""), ':')))
                .map(Map.Entry::getValue)
                .findFirst();

        Supplier<Optional<ValidatedColor.ColorHolder>> o2 = () -> customColorSetting.color_override_by_modid
                .entrySet()
                .stream()
                .filter(x -> Checker.checkIsThisMod(lbItemEntity, x.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

        Stream.of(o, o1, o2)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .ifPresent(x -> {
                    result.set(lbItemEntity.to(lbItemEntity.rarity().configModifyColor(new Color(x.argb(), true))));
                });

        return result.get();
    }
}
