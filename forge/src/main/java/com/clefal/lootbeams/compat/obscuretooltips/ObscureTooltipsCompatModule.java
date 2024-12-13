package com.clefal.lootbeams.compat.obscuretooltips;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.compat.ILBCompatModule;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.obscuria.tooltips.ObscureTooltips;
import net.minecraftforge.fml.ModList;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class ObscureTooltipsCompatModule implements ILBCompatModule {
    public final static ObscureTooltipsCompatModule INSTANCE = new ObscureTooltipsCompatModule();

    @SubscribeEvent(priority = EventPriority.LOW)
    public void removeRarity(TooltipsGatherNameAndRarityEvent event) {
        if (ConfigurationManager.request(Config.ENABLE_TOOLTIPS) == TooltipsEnableStatus.TooltipsStatus.NAME_RARITY_TOOLTIPS){
            event.gather.remove(TooltipsGatherNameAndRarityEvent.Case.RARITY);
        }
    }

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(ObscureTooltips.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()){
            Constants.LOGGER.info("Detected Obscure Tooltips, enable ObscureTooltipsCompatModule!");
            Constants.EVENT_BUS.register(INSTANCE);
        }
    }
}
