package com.clefal.lootbeams.compat.obscuretooltips;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.lootbeams.config.configs.TooltipsConfig;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.obscuria.tooltips.ObscureTooltips;
import net.minecraftforge.fml.ModList;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class ObscureTooltipsCompatModule implements ILBCompatModule {
    public final static ObscureTooltipsCompatModule INSTANCE = new ObscureTooltipsCompatModule();

    @SubscribeEvent(priority = EventPriority.LOW)
    public void removeRarity(TooltipsGatherNameAndRarityEvent event) {
        if (TooltipsConfig.tooltipsConfig.tooltips_enable_status == TooltipsEnableStatus.TooltipsStatus.NAME_AND_RARITY_IN_TOOLTIPS) {
            event.gather.remove(TooltipsGatherNameAndRarityEvent.Case.RARITY);
        }
    }

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(ObscureTooltips.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Obscure Tooltips, enable ObscureTooltipsCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }
}
