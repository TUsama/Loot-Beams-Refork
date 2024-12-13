package com.clefal.lootbeams.modules.tooltip;

import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.data.LBItemEntity;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import io.vavr.Function1;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class TooltipsEnableStatus {
    public static final String NAME = "name";
    public static final String RARITY = "rarity";
    public static final String TOOLTIPS = "tooltips";
    public static final Function1<LBItemEntity, Component> handleName = lbItemEntity -> {
        Boolean ifShowStack = ConfigurationManager.<Boolean>request(Config.RENDER_STACKCOUNT);
        ItemStack item = lbItemEntity.item().getItem();
        Style style = item.getHoverName().getStyle();
        if (!ifShowStack) return item.getHoverName();
        int count = item.getCount();
        if (count > 1) {
            return item.getHoverName().plainCopy().append(" x" + count).withStyle(style);
        }
        return item.getHoverName();
    };

    public enum TooltipsStatus {
        NONE((event) -> {
            throw new UnsupportedOperationException("can't extract Components on NONE status!");
        }),
        ONLY_NAME((event) ->
                event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity))
        ),
        NAME_AND_RARITY((event) -> {
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity));
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.RARITY, event.lbItemEntity.rarity().name());
        }),
        NAME_RARITY_TOOLTIPS((event) -> {
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.NAME, handleName.apply(event.lbItemEntity));
            event.gather.put(TooltipsGatherNameAndRarityEvent.Case.RARITY, event.lbItemEntity.rarity().name());
        });


        public final Consumer<TooltipsGatherNameAndRarityEvent> extractComponents;

        TooltipsStatus(Consumer<TooltipsGatherNameAndRarityEvent> extractComponents) {
            this.extractComponents = extractComponents;
        }


    }
}
