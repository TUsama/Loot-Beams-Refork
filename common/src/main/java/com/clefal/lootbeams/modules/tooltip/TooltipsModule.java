package com.clefal.lootbeams.modules.tooltip;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.TooltipsConfig;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.modules.ILBModule;
import com.clefal.lootbeams.modules.tooltip.nametag.NameTagRenderer;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;


import java.util.Map;

public class TooltipsModule implements ILBModule {

    public final static TooltipsModule INSTANCE = new TooltipsModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderNameTag(EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent event) {
        if (TooltipsConfig.tooltipsConfig.tooltips_enable_status == TooltipsEnableStatus.TooltipsStatus.NAME_AND_RARITY_IN_TOOLTIPS)
            return;
        NameTagRenderer.renderNameTag(event.poseStack, event.buffers, event.LBItemEntity);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void colorizeNameAndRarity(TooltipsGatherNameAndRarityEvent event){
        for (Map.Entry<TooltipsGatherNameAndRarityEvent.Case, Component> caseComponentEntry : event.gather.entrySet()) {
            Style oldStyle = caseComponentEntry.getValue().getStyle();
            caseComponentEntry.setValue(caseComponentEntry.getValue().plainCopy().withStyle(oldStyle).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(event.lbItemEntity.rarity().color().getRGB()))));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void InternalNameAndRarityCollector(TooltipsGatherNameAndRarityEvent event){
        TooltipsEnableStatus.TooltipsStatus status = TooltipsConfig.tooltipsConfig.tooltips_enable_status;
        status.extractComponents.accept(event);
    }


    @Override
    public void tryEnable() {
        LootBeamsConstants.EVENT_BUS.register(INSTANCE);
    }
}
