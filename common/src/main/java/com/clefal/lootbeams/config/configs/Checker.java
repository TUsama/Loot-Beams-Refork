package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
@UtilityClass
public class Checker {
    public boolean checkItemEquality(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {
        Item registryItem = BuiltInRegistries.ITEM.get(resourceLocation);
        return lbItemEntity.item().getItem().is(registryItem.asItem());
    }

    public boolean checkTagContainItem(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {
        TagKey<Item> itemTagKey = TagKey.create(
                // The registry key. The type of the registry must match the generic type of the tag.
                Registries.ITEM,
                // The location of the tag. This example will put our tag at data/examplemod/tags/blocks/example_tag.json.
                resourceLocation
        );
        return lbItemEntity.item().getItem().is(itemTagKey);
    }

    public boolean checkIsThisMod(LBItemEntity lbItemEntity, String modId) {
        return BuiltInRegistries.ITEM.getKey(lbItemEntity.item().getItem().getItem()).getNamespace().equals(modId);
    }
}
