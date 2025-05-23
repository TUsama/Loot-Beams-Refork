package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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
        ItemStack item = lbItemEntity.item().getItem();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item.getItem());
        AtomicReference<LBItemEntity> result = new AtomicReference<>(lbItemEntity);
        Supplier<Optional<ValidatedColor.ColorHolder>> o = () -> {
            ValidatedColor.ColorHolder colorHolder = customColorSetting.color_override_by_name.get(resourceLocation);
            if (colorHolder == null) return Optional.empty();
            return Optional.of(colorHolder);
        };


        Supplier<Optional<ValidatedColor.ColorHolder>> o1 = () -> {
            return item.getTags()
                    .map(x -> x.location().toString())
                    .filter(x -> customColorSetting.color_override_by_tag.containsKey("#" + x))
                    .findFirst()
                    .map(string -> customColorSetting.color_override_by_tag.get("#" + string));

        };

        Supplier<Optional<ValidatedColor.ColorHolder>> o2 = () -> Optional.ofNullable(customColorSetting.color_override_by_modid.get(resourceLocation.getNamespace()));

        Stream.of(o, o1, o2)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .ifPresent(x -> {
                    result.set(lbItemEntity.to(lbItemEntity.rarity().configModifyColor(LBColor.of(x.argb()))));
                });

        return result.get();
    }
}
