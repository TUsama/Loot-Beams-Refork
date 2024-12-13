package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeChecker implements IServicesChecker {

    @Override
    public boolean checkItemEquality(LBItemEntity lbItemEntity, String resourceLocation) {
        ResourceLocation location = ResourceLocation.tryParse(resourceLocation);
        if (location == null) return false;
        Item registryItem = ForgeRegistries.ITEMS.getValue(location);
        return registryItem != null && registryItem.asItem() == lbItemEntity.item().getItem().getItem();
    }

    @Override
    public boolean checkTagContainItem(LBItemEntity lbItemEntity, String tagWithoutNumberSign) {
        ResourceLocation location = ResourceLocation.tryParse(tagWithoutNumberSign);
        if (location == null) return false;
        return ForgeRegistries.ITEMS.tags().getTag(TagKey.create(BuiltInRegistries.ITEM.key(), location)).contains(lbItemEntity.item().getItem().getItem());
    }

    @Override
    public boolean checkIsThisMod(LBItemEntity lbItemEntity, String modId) {
        return ForgeRegistries.ITEMS.getKey(lbItemEntity.item().getItem().getItem()).getNamespace().equals(modId);
    }
}
