package com.clefal.lootbeams.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.clefal.lootbeams.config.ConfigHandlers;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.modules.ILBModuleRenderCache;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class LBItemEntityCache implements ILBModuleRenderCache<InternalLBItemEntityProvider, ItemEntity> {

    private final static Cache<ItemStack, LBItemEntity> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build();
    private final static LBItemEntityCache INSTANCE = new LBItemEntityCache();
    private final static Object lock = new Object();

    private static boolean mark = false;

    public static LBItemEntity ask(ItemEntity entity) {
        ItemStack item = entity.getItem();
        LBItemEntity ifPresent = cache.getIfPresent(item);
        if (ifPresent == null) {
            INSTANCE.handle(InternalLBItemEntityProvider.INSTANCE, entity, mark);
        }
        //System.out.println(cache.getIfPresent(item).rarity());
        return cache.getIfPresent(item);
    }



    protected static void provide(ItemEntity entity, LBItemEntity itemWithRarity) {
        cache.put(entity.getItem(), itemWithRarity);
    }

    @Override
    public BiConsumer<InternalLBItemEntityProvider, ItemEntity> getDataHandler() {
        return ((internalLbItemEntityProvider, itemEntity) -> {
            LBItemEntity lbItemEntity = InternalLBItemEntityProvider.getLBItemEntity(itemEntity);
            //override by config
            for (ModifyingConfigHandler handler : ConfigHandlers.INSTANCE.getHandlers()) {
                lbItemEntity = handler.modify(lbItemEntity);
            }

            provide(itemEntity, lbItemEntity);
            mark = false;
        });
    }
}
