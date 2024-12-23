package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import lombok.experimental.UtilityClass;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@UtilityClass
public class Checker {

    public boolean checkItemEquality(ItemStack itemStack, ResourceLocation resourceLocation) {
        Item registryItem = BuiltInRegistries.ITEM.get(resourceLocation);
        return itemStack.is(registryItem.asItem());
    }

    public boolean checkTagContainItem(ItemStack itemStack, ResourceLocation resourceLocation) {
        TagKey<Item> itemTagKey = TagKey.create(
                // The registry key. The type of the registry must match the generic type of the tag.
                Registries.ITEM,
                // The location of the tag. This example will put our tag at data/examplemod/tags/blocks/example_tag.json.
                resourceLocation
        );
        return itemStack.is(itemTagKey);
    }

    public boolean checkIsThisMod(ItemStack itemStack, String modId) {
        return BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace().equals(modId);
    }

    public boolean checkItemInItemList(ItemStack itemStack, ValidatedSet<ResourceLocation> resourceLocations) {
        ResourceLocation registryItem = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return resourceLocations.contains(registryItem);
    }

    public boolean checkItemHasTagInTagList(ItemStack itemStack, ValidatedSet<String> tags) {
        return itemStack.getTags().map(x -> x.location().toString()).anyMatch(tags::contains);
    }

    public boolean checkIsInThisModList(ItemStack itemStack, ValidatedSet<String> modId) {
        return modId.contains(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace());
    }

    public boolean checkItemInItemList(LBItemEntity lbItemEntity, ValidatedSet<ResourceLocation> resourceLocations) {
        return resourceLocations.contains(lbItemEntity.resourceLocation());
    }

    public boolean checkItemHasTagInTagList(LBItemEntity lbItemEntity, ValidatedSet<String> tags) {
        return lbItemEntity.item().getItem().getTags().map(x -> x.location().toString()).anyMatch(tags::contains);
    }

    public boolean checkIsInThisModList(LBItemEntity lbItemEntity, ValidatedSet<String> modId) {
        return modId.contains(lbItemEntity.resourceLocation().getNamespace());
    }
}
