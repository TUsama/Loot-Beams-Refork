package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeChecker implements IServicesChecker {

    @Override
    public boolean checkItemEquality(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {

        Item registryItem = ForgeRegistries.ITEMS.getValue(resourceLocation);
        return registryItem != null && registryItem.asItem() == lbItemEntity.item().getItem().getItem();
    }

    @Override
    public boolean checkTagContainItem(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {

        return ForgeRegistries.ITEMS.tags().getTag(TagKey.create(BuiltInRegistries.ITEM.key(), resourceLocation)).contains(lbItemEntity.item().getItem().getItem());
    }

    @Override
    public boolean checkIsThisMod(LBItemEntity lbItemEntity, ResourceLocation modId) {
        if (!modId.getPath().isBlank()) return false;
        return ForgeRegistries.ITEMS.getKey(lbItemEntity.item().getItem().getItem()).getNamespace().equals(modId.getNamespace());
    }
}
