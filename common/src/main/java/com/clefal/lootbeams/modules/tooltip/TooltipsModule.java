package com.clefal.lootbeams.modules.tooltip;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.LootInfomationConfig;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.modules.ILBModule;
import com.clefal.lootbeams.modules.tooltip.nametag.NameTagRenderer;
import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;


import java.util.Map;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.$;
import static com.clefal.nirvana_lib.relocated.io.vavr.API.Case;

public class TooltipsModule implements ILBModule {

    public final static TooltipsModule INSTANCE = new TooltipsModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderNameTag(EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent event) {
        if (LootInfomationConfig.lootInfomationConfig.lootInformationControl.loot_information_status == LootInformationEnableStatus.LootInformationStatus.NAME_AND_RARITY_IN_TOOLTIPS)
            return;
        NameTagRenderer.renderNameTag(event.poseStack, event.buffers, event.LBItemEntity);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void colorizeNameAndRarity(TooltipsGatherNameAndRarityEvent event){
        for (Map.Entry<TooltipsGatherNameAndRarityEvent.Case, Component> caseComponentEntry : event.gather.entrySet()) {
            Style oldStyle = caseComponentEntry.getValue().getStyle();
            if (oldStyle.equals(Style.EMPTY)){
                caseComponentEntry.setValue(MutableComponent.create(new LiteralContents(caseComponentEntry.getValue().getString())).withStyle(oldStyle).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(event.lbItemEntity.rarity().color().getRGB()))));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void deleteRarityInfoWhenConfigEnable(TooltipsGatherNameAndRarityEvent event){
        API.Match(LootInfomationConfig.lootInfomationConfig.rarity.showRarityFor).option(
                Case($(x -> x == LootInfomationConfig.ShowRarityTarget.NONE), x -> event.gather.remove(TooltipsGatherNameAndRarityEvent.Case.RARITY)),
                Case($(x -> x == LootInfomationConfig.ShowRarityTarget.RARE && !event.lbItemEntity.isRare()), x -> event.gather.remove(TooltipsGatherNameAndRarityEvent.Case.RARITY))
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void InternalNameAndRarityCollector(TooltipsGatherNameAndRarityEvent event){
        LootInformationEnableStatus.LootInformationStatus status = LootInfomationConfig.lootInfomationConfig.lootInformationControl.loot_information_status;
        status.extractComponents.accept(event);
    }


    @Override
    public void tryEnable() {
        LootBeamsConstants.EVENT_BUS.register(INSTANCE);
    }
}
