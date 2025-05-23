package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.nirvana_lib.relocated.io.vavr.Function1;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import net.minecraft.world.entity.item.ItemEntity;

@FunctionalInterface
public interface ILBRarityApplier extends Function1<ItemEntity, Option<LBItemEntity>> {
}
