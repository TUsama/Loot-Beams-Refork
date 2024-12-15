package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NeoForgeChecker implements IServicesChecker {

    @Override
    public boolean checkItemEquality(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {

        Item registryItem = BuiltInRegistries.ITEM.get(resourceLocation);
        return registryItem.asItem() == lbItemEntity.item().getItem().getItem();
    }

    @Override
    public boolean checkTagContainItem(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {
        TagKey<Item> itemTagKey = TagKey.create(
                // The registry key. The type of the registry must match the generic type of the tag.
                Registries.ITEM,
                // The location of the tag. This example will put our tag at data/examplemod/tags/blocks/example_tag.json.
                resourceLocation
        );
        return lbItemEntity.item().getItem().is(itemTagKey);
    }

    @Override
    public boolean checkIsThisMod(LBItemEntity lbItemEntity, ResourceLocation modId) {
        if (!modId.getPath().isBlank()) return false;
        return BuiltInRegistries.ITEM.getKey(lbItemEntity.item().getItem().getItem()).getNamespace().equals(modId.getNamespace());
    }
}
