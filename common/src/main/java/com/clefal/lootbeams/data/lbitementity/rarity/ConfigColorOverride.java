package com.clefal.lootbeams.data.lbitementity.rarity;

import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigColorOverride extends ModifyingConfigHandler {



    private final ValidatedIdentifierMap<ValidatedColor.ColorHolder> overrides = LightConfig.lightConfig.customColorSetting.color_override;

    public ConfigColorOverride() {

    }


    @Override
    public LBItemEntity modify(LBItemEntity lbItemEntity) {
        if (!LightConfig.lightConfig.customColorSetting.enable_custom_color) return lbItemEntity;
        AtomicReference<LBItemEntity> result = new AtomicReference<>(lbItemEntity);
        if (!overrides.isEmpty()) {

            overrides.get().entrySet().stream()
                    .filter(x -> {
                        IServicesChecker checker = PlatformChecker.PLATFORM;
                        ResourceLocation key = x.getKey();
                        return checker.checkItemEquality(lbItemEntity, key) ||  checker.checkTagContainItem(lbItemEntity, key) || checker.checkIsThisMod(lbItemEntity, key);
                    })
                    .findFirst()
                    .ifPresent(x -> {
                        result.set(lbItemEntity.to(LBRarity.of(lbItemEntity.rarity().name(), new Color(x.getValue().argb(), true), lbItemEntity.rarity().absoluteOrdinal())));
                    });


        }
        return result.get();
    }
}
