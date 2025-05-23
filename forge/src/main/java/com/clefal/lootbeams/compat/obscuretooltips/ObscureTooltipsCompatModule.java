package com.clefal.lootbeams.compat.obscuretooltips;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.lootbeams.config.configs.LootInfomationConfig;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.modules.tooltip.LootInformationEnableStatus;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.obscuria.tooltips.ObscureTooltips;
import net.minecraftforge.fml.ModList;


public class ObscureTooltipsCompatModule implements ILBCompatModule {
    public final static ObscureTooltipsCompatModule INSTANCE = new ObscureTooltipsCompatModule();

    @SubscribeEvent(priority = EventPriority.LOW)
    public void removeRarity(TooltipsGatherNameAndRarityEvent event) {
        if (LootInfomationConfig.lootInfomationConfig.lootInformationControl.loot_information_status == LootInformationEnableStatus.LootInformationStatus.NAME_AND_RARITY_IN_TOOLTIPS) {
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
