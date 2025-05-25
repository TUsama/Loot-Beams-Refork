//? if forge {
/*package me.clefal.lootbeams.compat.forge_1_20_1.mine_and_slash;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.robertx22.orbs_of_crafting.register.ExileCurrency;

public class OrbOfCraftingCompatModule implements ILBCompatModule {
    public final static OrbOfCraftingCompatModule INSTANCE = new OrbOfCraftingCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("orbs_of_crafting");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()){
            LootBeamsConstants.LOGGER.info("Detected Orb Of Crafting, enable OrbOfCraftingCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void registerWhitelistCondition(RegisterConfigConditionEvent.RegisterWhitelistEvent event) {
        //currenccy, I prefer to show the currency always.
        event.conditions.add(lbItemEntity -> ExileCurrency.get(lbItemEntity.item().getItem()).isPresent());
    }
}
*///?}
