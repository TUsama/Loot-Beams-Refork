package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import net.neoforged.bus.api.SubscribeEvent;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.socket.gem.GemItem;
import net.neoforged.fml.ModList;

import java.awt.*;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.*;

public class ApotheosisCompatModule implements ILBCompatModule {
    public final static ApotheosisCompatModule INSTANCE = new ApotheosisCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Apotheosis, enable ApotheosisCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();

            return Match(Match(stack).option(
                    //ItemStack -> LootRarity
                    Case($(itemStack -> AffixHelper.hasAffixes(itemStack)), itemStack -> AffixHelper.getRarity(itemStack)),
                    Case($(v -> (v.getItem() instanceof SalvageItem)), v -> RarityRegistry.getMaterialRarity(v.getItem()))
            )).of(
                    //LootRarity -> ILBRarity
                    Case($(v -> v.isEmpty() || v.get().is(RarityRegistry.INSTANCE.emptyHolder().getId())), v -> Option.none()),
                    Case($(v -> !v.get().isBound()), v -> Option.none()),
                    Case($(), v -> Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    v.get().get().toComponent(),
                                    LBColor.ofMutable(v.get().get().color()),
                                    v.get().get().sortIndex()
                            )))
                    ));
        });
        //handle new gem item.
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();
            return Option.of(stack)
                    .filter(x -> x.getItem() instanceof GemItem)
                    .map(x -> GemInstance.unsocketed(x).purity())
                    .map(x -> LBItemEntity.of(
                            itemEntity,
                            LBRarity.of(
                                    x.toComponent(),
                                    LBColor.ofMutable(x.getColor()),
                                    x.ordinal()
                            )
                    ));
        });

    }
    //put gem to whitelist
    @SubscribeEvent
    public void registerWhitelist(RegisterConfigConditionEvent.RegisterWhitelistEvent event){
        event.conditions.add(x -> x.item().getItem().getItem() instanceof GemItem || x.item().getItem().getItem() instanceof SalvageItem ||AffixHelper.hasAffixes(x.item().getItem()));
    }
}
