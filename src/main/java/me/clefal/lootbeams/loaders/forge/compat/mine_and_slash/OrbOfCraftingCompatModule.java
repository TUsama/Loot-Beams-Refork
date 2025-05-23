//? if forge {

package me.clefal.lootbeams.loaders.forge.compat.mine_and_slash;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.robertx22.addons.orbs_of_crafting.currency.IItemAsCurrency;
import com.robertx22.orbs_of_crafting.register.ExileCurrency;
import net.minecraftforge.fml.ModList;

public class OrbOfCraftingCompatModule implements ILBCompatModule {
    public final static OrbOfCraftingCompatModule INSTANCE = new OrbOfCraftingCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded("orbs_of_crafting");
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

 //?}
