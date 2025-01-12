package com.clefal.lootbeams.modules.tooltip;

import com.clefal.lootbeams.config.configs.LootInfomationConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.nirvana_lib.relocated.io.vavr.Function1;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class LootInformationEnableStatus {
    public static final Function1<LBItemEntity, Component> handleName = lbItemEntity -> {
        boolean ifShowStack = LootInfomationConfig.lootInfomationConfig.nameTag.render_stack_count;
        ItemStack item = lbItemEntity.item().getItem();
        Style style = item.getHoverName().getStyle();
        if (!ifShowStack) return item.getHoverName();
        int count = item.getCount();
        if (count > 1) {

            return MutableComponent.create(new PlainTextContents.LiteralContents(item.getHoverName().getString())).append(" x" + count).withStyle(style);
        }
        return item.getHoverName();
    };

    public enum LootInformationStatus {
        NONE((event) -> {
            throw new UnsupportedOperationException("can't extract Components on NONE status!");
        }),
        ONLY_NAME_IN_NAMETAG((event) ->
                event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity))
        ),
        NAME_AND_RARITY_IN_NAMETAG((event) -> {
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity));
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.RARITY, event.lbItemEntity.rarity().name());
        }),
        NAME_AND_RARITY_IN_TOOLTIPS((event) -> {
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity));
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.RARITY, event.lbItemEntity.rarity().name());
        });


        public final Consumer<TooltipsGatherNameAndRarityEvent> extractComponents;

        LootInformationStatus(Consumer<TooltipsGatherNameAndRarityEvent> extractComponents) {
            this.extractComponents = extractComponents;
        }


    }
}
