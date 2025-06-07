//? if 1.20.1 || (1.21.1 && neoforge) {
package me.clefal.lootbeams.compat.multiversion_compat;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import com.sammy.malum.MalumMod;

import com.sammy.malum.common.item.spirit.RitualShardItem;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;

//? if 1.21.1 {
import com.sammy.malum.common.data.component.RitualDataComponent;
import com.sammy.malum.registry.common.item.MalumDataComponents;
//?}

public class MalumCompatModule implements ILBCompatModule {
    public static final MalumCompatModule INSTANCE = new MalumCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(MalumMod.MALUM);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Malum, enable MalumCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity ->
                API.Option(itemEntity.getItem())
                        //? if 1.20.1 {
                        /*.flatMap(x -> Option.of(RitualShardItem.getRitualType(x)))
                        .map(x -> x.spirit)
                        *///?} else {
                        .flatMap(x -> {
                            RitualDataComponent data = x.get(MalumDataComponents.RITUAL_DATA);
                            if (data != null) {
                                return Option.some(data.ritualType().spirit);
                            }
                            return Option.none();
                        })
                        //?}
                        .map(x -> {
                            LBRarity old = LBRarity.ofVanillaRarity(x.getItemRarity());
                            return LBItemEntity.of(itemEntity,
                                    LBRarity.of(
                                            old.name(),
                                            LBColor.ofMutable(x.getTextColor(false)),
                                            -1
                                    ));
                        })
);
    }
}
//?}
