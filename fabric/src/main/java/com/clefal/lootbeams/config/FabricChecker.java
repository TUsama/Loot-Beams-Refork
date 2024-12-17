package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class FabricChecker implements IServicesChecker {
    @Override
    public boolean checkItemEquality(LBItemEntity lbItemEntity, ResourceLocation resourceLocation) {
        return BuiltInRegistries.ITEM.get(resourceLocation).equals(lbItemEntity.item().getItem().getItem());
    }

    @Override
    public boolean checkTagContainItem(LBItemEntity lbItemEntity, ResourceLocation tagWithoutNumberSign) {
        return lbItemEntity.item().getItem().is(TagKey.create(Registries.ITEM, tagWithoutNumberSign));
    }

    @Override
    public boolean checkIsThisMod(LBItemEntity lbItemEntity, ResourceLocation modId) {
        if (!modId.getPath().isBlank()) return false;
        return BuiltInRegistries.ITEM.getKey(lbItemEntity.item().getItem().getItem()).getNamespace().equals(modId.getNamespace());
    }
}
